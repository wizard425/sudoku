package backend;

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
		int[][] ret = this.handler.createNewGame(uid.toString(), 1).getFieldInInts();

		return Response.status(200).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("access-control-expose-headers", "gameid")
				.header("gameid", uid).entity(ret).build();

	}
	
	@GET
	@Path("/create/{difficulty}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createGameWithDifficulty(@PathParam("difficulty") int diff) throws JsonProcessingException {
		UUID uid = UUID.randomUUID();
		int[][] ret = this.handler.createNewGame(uid.toString(), 1).getFieldInInts();

		return Response.status(200).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("gameId", uid).entity(ret).build();

	}
	
	@GET
	@Path("/{gameId}/field")
	@Produces(MediaType.APPLICATION_JSON)
	public Response Game(@PathParam("gameId") UUID gameId) throws JsonProcessingException {
		
		int[][] ret = this.handler.getGame(gameId.toString()).getFieldInInts();

		return Response.status(200).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(ret).build();

	}

	@GET
	@Path("/{gameId}/setnumber/{y}/{x}/{placed}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response resucedmoves(@PathParam("gameId") UUID gameId, @PathParam("y") int y, @PathParam("x") int x, @PathParam("placed") int placed)
			throws JsonProcessingException {

		Game gameOfUser = this.handler.getGame(gameId.toString());
		
		if (gameOfUser.makeMove(y, x, placed)) {
			SudokuFunctions.visualizeInTerminal(gameOfUser.getFieldInInts());
			int[][] ret =gameOfUser.gamefield;
			return Response.status(200).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.entity(ret)
					.build();
		} else {
			int[][] ret =gameOfUser.gamefield;
			return Response.status(400).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
					.entity(ret).build();
		}
	}

	@GET
	@Path("/{gameId}/reset")
	@Produces(MediaType.APPLICATION_JSON)
	public Response reset(@PathParam("gameId") UUID gameId) throws JsonProcessingException {
		Game gameOfUser = this.handler.getGame(gameId.toString());

		gameOfUser.resetGame();
		int[][] ret = gameOfUser.gamefield;
		return Response.status(200).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(ret).build();

	}

	@GET
	@Path("/{gameId}/solve")
	@Produces(MediaType.APPLICATION_JSON)
	public Response solve(@PathParam("gameId") UUID gameId) throws JsonProcessingException {
		Game gameOfUser = this.handler.getGame(gameId.toString());
		int[][] ret = SudokuFunctions.solve(gameOfUser.gamefield);
		return Response.status(200).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("gameId", gameId.toString()).entity(ret).build();

	}

}