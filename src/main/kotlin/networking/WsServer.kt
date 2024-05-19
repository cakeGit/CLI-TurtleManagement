package networking

import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress
import java.nio.ByteBuffer


class WsServer(address: InetSocketAddress?) : WebSocketServer(address) {
    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        conn.send("Welcome to the server!") //This method sends a message to the new client
        broadcast("new connection: " + handshake.resourceDescriptor) //This method sends a message to all clients connected
        println("new connection to " + conn.getRemoteSocketAddress())
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        println("closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason)
    }

    override fun onMessage(conn: WebSocket, message: String) {
        println("[${conn.getRemoteSocketAddress()}] Plaintext Message: $message")
    }

    override fun onMessage(conn: WebSocket, message: ByteBuffer?) {
        println("[${conn.getRemoteSocketAddress()}] Encoded Message: $message")
    }

    override fun onError(conn: WebSocket, ex: Exception) {
        println("an error occurred on connection " + conn.getRemoteSocketAddress() + ":" + ex)
    }

    override fun onStart() {
        println("server started successfully")
    }

    companion object {

        fun run() {
            val host = "localhost"
            val port = 8887
            val server: WebSocketServer = WsServer(InetSocketAddress(host, port))
            server.run()
        }
    }

}