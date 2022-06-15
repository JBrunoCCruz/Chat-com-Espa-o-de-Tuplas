package tuplas;

import net.jini.space.JavaSpace;

public class Reader {

	public static void main(String[] args) {
		try {
			System.out.println("Procurando pelo serviço JavaSpace...");
			Lookup finder = new Lookup(JavaSpace.class);
			JavaSpace space = (JavaSpace) finder.getService();
			
			if (space == null) {
				System.out.println("O serviço JavaSpace não foi encontrado. Encerrando...");
				System.exit(0);
			}			
			System.out.println("O serviço JavaSpace foi encontrado.");
			
			while (true) {
				Message template = new Message();
				Message msg = (Message) space.take(template, null, 60 * 1000);
				
				if (msg == null) {
					System.out.println("Tempo de espera esgostado. Encerrando...");
					System.exit(0);
				}
				
				System.out.println("Mensagem recebida: " + msg.content);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
