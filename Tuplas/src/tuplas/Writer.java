package tuplas;

import net.jini.space.JavaSpace;
import java.util.Scanner;

public class Writer {

	public static void main(String[] args) {
		try {
			System.out.println("Procurando pelo servi�o JavaSpace...");
			Lookup finder = new Lookup(JavaSpace.class);
			JavaSpace space = (JavaSpace) finder.getService();
			
			if (space == null) {
				System.out.println("O servi�o JavaSpace n�o foi encontrado. Encerrando...");
				System.exit(-1);
			}			
			System.out.println("O servi�o JavaSpace foi encontrado.");
			
			Scanner scanner = new Scanner(System.in);
			
			while (true) {
				System.out.print("Entre com o texto da mensagem (ENTER para sair): ");
				String message = scanner.nextLine();
				if (message == null || message.equals("")) {
					System.exit(0);
				}
				Message msg = new Message();
				msg.content = message;
				space.write(msg, null, 60 * 1000);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
