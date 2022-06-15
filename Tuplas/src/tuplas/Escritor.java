package tuplas;

import net.jini.space.JavaSpace;
import java.util.Scanner;

public class Escritor {

	public static void main(String[] args) {
		try {
			System.out.println("Procurando pelo serviço JavaSpace...");
			Lookup finder = new Lookup(JavaSpace.class);
			JavaSpace space = (JavaSpace) finder.getService();
			
			if (space == null) {
				System.out.println("O serviço JavaSpace não foi encontrado. Encerrando...");
				System.exit(-1);
			}			
			System.out.println("O serviço JavaSpace foi encontrado.");
			
			//Scanner scanner = new Scanner(System.in);
			
			while (true) {
				//System.out.print("Entre com o texto da mensagem (ENTER para sair): ");
				/*String message = scanner.nextLine();
				if (message == null || message.equals("")) {
					System.exit(0);
				}*/
				TuplaTeste msg = new TuplaTeste();
				msg.nome = "Leo";
				msg.idade = 22;
				msg.rua = "AvenidaA";
				space.write(msg, null, 60 * 1000);
				System.out.println(msg.nome + " no espaço " + msg.idade + " " + msg.rua);
				
				msg.nome = "Bob";
				msg.idade = 25;
				msg.rua = "301";
				space.write(msg, null, 60 * 1000);
				System.out.println(msg.nome + " no espaço " + msg.idade + " " + msg.rua);
				
				msg.nome = "Eduardo";
				msg.idade = 23;
				msg.rua = "Bullevar3";
				space.write(msg, null, 60 * 1000);
				System.out.println(msg.nome + " no espaço " + msg.idade + " " + msg.rua);
				
				msg.nome = "Emanuel";
				msg.idade = 22;
				msg.rua = "AvenidaA";
				space.write(msg, null, 60 * 1000);
				System.out.println(msg.nome + " no espaço " + msg.idade + " " + msg.rua);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
