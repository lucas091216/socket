
package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vital
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException {
        try {
            //Estabelecer conexão com o cliente e o servidor
            System.out.println("Estabelecendo conexao...");
            Socket socket = new Socket("localhost", 5555);
            System.out.println("Conexao estabelecida.");
            
            //criar entrada e saida = streams
            
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            System.out.println("Enviando Mensagem...");
            
            
            
            
            Mensagem m = new Mensagem("HELLO");
            m.setStatus( Status.SOLICITACAO);
            m.setParam("nome","José");
            m.setParam("Sobrenome","Da silva");
            
            output.writeObject(m);
            output.flush(); //libera o buffer para o envio
            
            System.out.println("Mensagem: " + m + " enviada");
            
            m = (Mensagem)input.readObject();
            
            System.out.println("Resposta: " + m);
            
            if(m.getStatus() == Status.OK){
                String resposta = (String) m.getParam("Mensagem");
                System.out.println("Mensagem: "+ resposta);
            }else{
                System.out.println("Erro: "+ m.getStatus());
            }
            input.close();
            output.close();
            socket.close();
            
        } catch (IOException ex) {
            System.out.println("Erro no cliente " + ex);
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }catch(ClassNotFoundException ex){
            System.out.println("Erro no cast "+ ex.getMessage());
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }
    
}
