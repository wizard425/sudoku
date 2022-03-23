package backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Path("")
public class Endpoints {
	GameHandler handler = GameHandler.getInstance();

	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

	@GET
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createGame() throws JsonProcessingException {
		UUID uid = UUID.randomUUID();
		String ret = Arrays.deepToString(this.handler.createNewGame(uid.toString()).getFieldInInts());

		String json = ow.writeValueAsString(ret);

		return Response.status(200).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("gameId", uid).entity(json).build();

	}
	
	@GET
	@Path("/{gameId}/field")
	@Produces(MediaType.APPLICATION_JSON)
	public Response Game(@PathParam("gameId") UUID gameId) throws JsonProcessingException {
		
		String ret = Arrays.deepToString(this.handler.getGame(gameId.toString()).getFieldInInts());

		String json = ow.writeValueAsString(ret);

		return Response.status(200).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(json).build();

	}

	@GET
	@Path("/{gameId}/setnumber/{y}/{x}/{placed}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response resucedmoves(@PathParam("gameId") UUID gameId, @PathParam("y") int y, @PathParam("x") int x, @PathParam("placed") int placed)
			throws JsonProcessingException {

		Game gameOfUser = this.handler.getGame(gameId.toString());
		
		if (gameOfUser.makeMove(y, x, placed)) {
			SudokuFunctions.visualizeInTerminal(gameOfUser.getFieldInInts());
			String json = ow.writeValueAsString(gameOfUser.getFieldInInts());
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(json)
					.build();
		} else {

			return Response.status(400).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.entity("Nicht gueltig").build();
		}
	}

	@GET
	@Path("/{gameId}/reset")
	@Produces(MediaType.TEXT_PLAIN)
	public Response reset(@PathParam("gameId") UUID gameId) throws JsonProcessingException {
		Game gameOfUser = this.handler.getGame(gameId.toString());

		gameOfUser.resetGame();
		String json = ow.writeValueAsString(gameOfUser.getFieldInInts());
		return Response.status(200).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(json).build();

	}

	@GET
	@Path("/{gameId}/solve")
	@Produces(MediaType.TEXT_PLAIN)
	public Response solve(@PathParam("gameId") UUID gameId) throws JsonProcessingException {
		Game gameOfUser = this.handler.getGame(gameId.toString());

		String json = ow.writeValueAsString(gameOfUser.solve());
		return Response.status(200).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("gameId", gameId.toString()).entity(json).build();

	}

	/*
	 * @GET
	 * 
	 * @Path("/legalmoves/{color}")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response
	 * moves(@PathParam("color") String color) throws JsonProcessingException {
	 * String res = ""; HashMap<Point, ArrayList<Point>>[] temp = null; Point[][]
	 * liste = new Point[0][]; Point[][][] l = new Point[2][][]; if
	 * (color.equals("white")) { temp = s.getMoves(1); //liste=new
	 * Point[temp.length][][]; l = new Point[temp.length][][]; for (int i = 0; i <
	 * temp.length; i++) { HashMap<Point, ArrayList<Point>> map = temp[i];
	 * Set<Point> keysTemp = map.keySet(); ArrayList<Point> keys = new
	 * ArrayList<Point>(); keys.addAll(keysTemp); l[i] = new Point[keys.size()][];
	 * for (int j = 0; j < keys.size(); j++) { ArrayList<Point> moves =
	 * map.get(keys.get(j)); l[i][j] = new Point[moves.size() + 1]; l[i][j][0] =
	 * keys.get(i); for (int k = 0; k < moves.size(); k++) { l[i][j][k + 1] =
	 * moves.get(k); } } } } else if (color.equals("black")) { temp =
	 * s.getMoves(-1); //liste=new Point[temp.length][][]; l = new
	 * Point[temp.length][][]; for (int i = 0; i < temp.length; i++) {
	 * HashMap<Point, ArrayList<Point>> map = temp[i]; Set<Point> keysTemp =
	 * map.keySet(); ArrayList<Point> keys = new ArrayList<Point>();
	 * keys.addAll(keysTemp); l[i] = new Point[keys.size()][]; for (int j = 0; j <
	 * keys.size(); j++) { ArrayList<Point> moves = map.get(keys.get(j)); l[i][j] =
	 * new Point[moves.size() + 1]; l[i][j][0] = keys.get(i); for (int k = 0; k <
	 * moves.size(); k++) { l[i][j][k + 1] = moves.get(k); } } } } String json =
	 * ow.writeValueAsString(l); return Response .status(202)
	 * .header("Access-Control-Allow-Origin", "*")
	 * .header("Access-Control-Allow-Credentials", "true")
	 * .header("Access-Control-Allow-Headers",
	 * "origin, content-type, accept, authorization")
	 * .header("Access-Control-Allow-Methods",
	 * "GET, POST, PUT, DELETE, OPTIONS, HEAD") .entity(json) .build(); }
	 * 
	 * @GET
	 * 
	 * @Path("/checkgameover")
	 * 
	 * @Produces(MediaType.TEXT_PLAIN) public Response checkgameover() { int ret =
	 * this.s.checkGameOver(); return Response .status(202)
	 * .header("Access-Control-Allow-Origin", "*")
	 * .header("Access-Control-Allow-Credentials", "true")
	 * .header("Access-Control-Allow-Headers",
	 * "origin, content-type, accept, authorization")
	 * .header("Access-Control-Allow-Methods",
	 * "GET, POST, PUT, DELETE, OPTIONS, HEAD") .entity(ret) .build(); }
	 * 
	 * @GET
	 * 
	 * @Path("/auswertung")
	 * 
	 * @Produces(MediaType.TEXT_PLAIN) public Response auswertung() {
	 * Visualization.visualizeInTerminal(s.feld); int ret = s.auswertung(s.feld);
	 * System.out.println(ret); return Response .status(202)
	 * .header("Access-Control-Allow-Origin", "*")
	 * .header("Access-Control-Allow-Credentials", "true")
	 * .header("Access-Control-Allow-Headers",
	 * "origin, content-type, accept, authorization")
	 * .header("Access-Control-Allow-Methods",
	 * "GET, POST, PUT, DELETE, OPTIONS, HEAD") .entity(ret) .build(); }
	 * 
	 * @GET
	 * 
	 * @Path("/reset")
	 * 
	 * @Produces(MediaType.TEXT_PLAIN) public Response resetgame() { int[][] ret =
	 * this.s.resetGame(); s.white=false; String json = Arrays.deepToString(ret);
	 * return Response .status(202) .header("Access-Control-Allow-Origin", "*")
	 * .header("Access-Control-Allow-Credentials", "true")
	 * .header("Access-Control-Allow-Headers",
	 * "origin, content-type, accept, authorization")
	 * .header("Access-Control-Allow-Methods",
	 * "GET, POST, PUT, DELETE, OPTIONS, HEAD") .entity(json) .build(); }
	 * 
	 * @GET
	 * 
	 * @Path("/movearchitect/{ysource}/{xsource}/{ydestination}/{xdestination}")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response
	 * moves(@PathParam("xsource") String xsource, @PathParam("ysource") String
	 * ysource,
	 * 
	 * @PathParam("xdestination") String xdestination, @PathParam("ydestination")
	 * String ydestination) throws JsonProcessingException {
	 * System.out.println("Movearchiteckt"); int xs = Integer.parseInt(xsource); int
	 * xd = Integer.parseInt(xdestination); int ys = Integer.parseInt(ysource); int
	 * yd = Integer.parseInt(ydestination); Point sourcefield = s.feld[ys][xs]; if
	 * (sourcefield.compare(s.getArchitect1())) { s.setArchitect1(yd, xd); } else if
	 * (sourcefield.compare(s.getArchitect2())) { s.setArchitect2(yd, xd); }
	 * sourcefield.value = 0; Visualization.visualizeInTerminal(s.feld); int[][]
	 * feld2 = new int[9][9]; for (int i = 0; i < 9; i++) { for (int j = 0; j < 9;
	 * j++) { feld2[i][j] = s.feld[i][j].value; } } String json =
	 * ow.writeValueAsString(feld2); return Response .status(202)
	 * .header("Access-Control-Allow-Origin", "*")
	 * .header("Access-Control-Allow-Credentials", "true")
	 * .header("Access-Control-Allow-Headers",
	 * "origin, content-type, accept, authorization")
	 * .header("Access-Control-Allow-Methods",
	 * "GET, POST, PUT, DELETE, OPTIONS, HEAD") .entity(json) .build(); }
	 * 
	 * @GET
	 * 
	 * @Path("/currentcolor")
	 * 
	 * @Produces(MediaType.TEXT_PLAIN) public Response currentcolor() { int erg; if
	 * (s.white) { erg = 1; } else { erg = -1; }
	 * 
	 * return Response .status(202) .header("Access-Control-Allow-Origin", "*")
	 * .header("Access-Control-Allow-Credentials", "true")
	 * .header("Access-Control-Allow-Headers",
	 * "origin, content-type, accept, authorization")
	 * .header("Access-Control-Allow-Methods",
	 * "GET, POST, PUT, DELETE, OPTIONS, HEAD") .entity(erg) .build(); }
	 * 
	 * @GET
	 * 
	 * @Path("/move/{row}/{colum}/{typ}")
	 * 
	 * @Produces(MediaType.TEXT_PLAIN) public Response moves(@PathParam("row")
	 * String row, @PathParam("colum") String column, @PathParam("typ") String typ)
	 * throws JsonProcessingException { Point[][] temp =
	 * s.placeBuilding(Integer.parseInt(row), Integer.parseInt(column),
	 * Integer.parseInt(typ)); int[][] erg = new int[9][9]; for (int i = 0; i < 9;
	 * i++) { for (int j = 0; j < 9; j++) { erg[i][j] = temp[i][j].value; } } String
	 * json = ow.writeValueAsString(erg); return Response .status(202)
	 * .header("Access-Control-Allow-Origin", "*")
	 * .header("Access-Control-Allow-Credentials", "true")
	 * .header("Access-Control-Allow-Headers",
	 * "origin, content-type, accept, authorization")
	 * .header("Access-Control-Allow-Methods",
	 * "GET, POST, PUT, DELETE, OPTIONS, HEAD") .entity(json) .build(); }
	 * 
	 * /*@GET
	 * 
	 * @Path("/botmove")
	 * 
	 * @Produces(MediaType.TEXT_PLAIN) public Response botmove() throws
	 * JsonProcessingException { Spielfeld t = s.computermove(1); int[][] feld2 =
	 * new int[9][9]; for (int i = 0; i < 9; i++) { for (int j = 0; j < 9; j++) {
	 * feld2[i][j] = t.feld[i][j].value; } } s.white = !s.white; String json =
	 * ow.writeValueAsString(feld2); return Response .status(200)
	 * .header("Access-Control-Allow-Origin", "*")
	 * .header("Access-Control-Allow-Credentials", "true")
	 * .header("Access-Control-Allow-Headers",
	 * "origin, content-type, accept, authorization")
	 * .header("Access-Control-Allow-Methods",
	 * "GET, POST, PUT, DELETE, OPTIONS, HEAD") .entity(json) .build(); }
	 * 
	 * @GET
	 * 
	 * @Path("/easymove")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response easymove(){ Point[][]
	 * temp; temp = s.crashfunction(); s.feld = temp; int[][] feld2 = new int[9][9];
	 * for (int i = 0; i < 9; i++) { for (int j = 0; j < 9; j++) { feld2[i][j] =
	 * s.feld[i][j].value; } } s.white = !s.white; s.locate_architects();
	 * Visualization.visualizeInTerminal(temp); String json = "ffasdfasdfasd"; try {
	 * json = ow.writeValueAsString(feld2); } catch (JsonProcessingException e) {
	 * e.printStackTrace(); }
	 * 
	 * return Response .status(200) .header("Access-Control-Allow-Origin", "*")
	 * .header("Access-Control-Allow-Credentials", "true")
	 * .header("Access-Control-Allow-Headers",
	 * "origin, content-type, accept, authorization")
	 * .header("Access-Control-Allow-Methods",
	 * "GET, POST, PUT, DELETE, OPTIONS, HEAD") .entity(json) .build();
	 * 
	 * }
	 */

}