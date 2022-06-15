package chat;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.Insets;
import java.awt.desktop.ScreenSleepEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import net.jini.space.JavaSpace;

import java.util.ArrayList;
import java.util.Scanner;

public class TelaDoChat {

	private JFrame frame;
	private JTextField textNomeDaNovaSala;
	
	static Lookup finder;
	static JavaSpace space;
	
	static Thread recebeMsg, recebeMsgPriv;
	static Boolean recebeMsgFim = false, recebeMsgPrivFim = false;
	
	public static String minhaSala;
	public static String meuNome;
	public static int indiceDaMensagemNaHoraQueEntrei = 0;
	public static int quantMsgLidasEnviadasAposEntrarNaSala = 0;
	public static int quantMsgPrivadasRecebidas = 0;
	
	public static JTextArea textAreaChat;
	
	private JTextField txtMinhaMensagemChat;
	private JTextField txtNomeDeUsuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					TelaDoChat window = new TelaDoChat();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaDoChat() {		
		procuraPeloSevicoJavaSpace();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JComboBox comboBoxListaDeSalas = new JComboBox();
		comboBoxListaDeSalas.setMaximumRowCount(4);
		comboBoxListaDeSalas.setBounds(197, 70, 352, 25);		
		frame.getContentPane().add(comboBoxListaDeSalas);		
		
		textNomeDaNovaSala = new JTextField();
		textNomeDaNovaSala.setMargin(new Insets(2, 5, 2, 2));
		textNomeDaNovaSala.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textNomeDaNovaSala.setBounds(197, 25, 352, 25);
		frame.getContentPane().add(textNomeDaNovaSala);
		textNomeDaNovaSala.setColumns(10);
		
		JLabel lblConfirmNovaSala = new JLabel("Aguardando confirma\u00E7\u00E3o...");
		lblConfirmNovaSala.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblConfirmNovaSala.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfirmNovaSala.setBounds(566, 28, 293, 20);
		frame.getContentPane().add(lblConfirmNovaSala);
		
		JLabel lblConfirmSalaExistente = new JLabel("Aguardando confirma\u00E7\u00E3o...");
		lblConfirmSalaExistente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblConfirmSalaExistente.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfirmSalaExistente.setBounds(570, 74, 289, 20);
		frame.getContentPane().add(lblConfirmSalaExistente);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(18, 200, 850, 14);
		frame.getContentPane().add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		scrollPane.setBounds(166, 250, 378, 91);
		frame.getContentPane().add(scrollPane);
		
		textAreaChat = new JTextArea("");
		textAreaChat.setEditable(false);
		textAreaChat.setLineWrap(true);
		textAreaChat.setMargin(new Insets(3, 3, 3, 3));
		scrollPane.setViewportView(textAreaChat);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setAutoscrolls(true);
		scrollPane_1.setBounds(626, 250, 144, 89);
		frame.getContentPane().add(scrollPane_1);
		
		JTextArea textAreaPessoas = new JTextArea();
		textAreaPessoas.setLineWrap(true);
		textAreaPessoas.setEditable(false);
		scrollPane_1.setViewportView(textAreaPessoas);
		
		JLabel lblChat = new JLabel("Chat");
		lblChat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblChat.setHorizontalAlignment(SwingConstants.CENTER);
		lblChat.setBounds(330, 220, 46, 14);
		frame.getContentPane().add(lblChat);
		
		JLabel lblPessoas = new JLabel("Pessoas na sala");
		lblPessoas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPessoas.setHorizontalAlignment(SwingConstants.CENTER);
		lblPessoas.setBounds(645, 220, 100, 14);
		frame.getContentPane().add(lblPessoas);
		
		JLabel lblIndicaSala = new JLabel("Sala");
		lblIndicaSala.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIndicaSala.setForeground(new Color(0, 0, 0));
		lblIndicaSala.setHorizontalAlignment(SwingConstants.CENTER);
		lblIndicaSala.setBounds(26, 260, 100, 20);
		frame.getContentPane().add(lblIndicaSala);
		
		JLabel lblIndicaSalaAtual = new JLabel("...");
		lblIndicaSalaAtual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIndicaSalaAtual.setHorizontalAlignment(SwingConstants.CENTER);
		lblIndicaSalaAtual.setForeground(new Color(255, 69, 0));
		lblIndicaSalaAtual.setBounds(26, 290, 100, 20);
		frame.getContentPane().add(lblIndicaSalaAtual);
		
		JComboBox comboBoxListaDePessoas = new JComboBox();
		comboBoxListaDePessoas.setMaximumRowCount(1);
		comboBoxListaDePessoas.setBounds(346, 400, 200, 24);
		frame.getContentPane().add(comboBoxListaDePessoas);
		
		txtMinhaMensagemChat = new JTextField();
		txtMinhaMensagemChat.setMargin(new Insets(2, 3, 2, 3));
		txtMinhaMensagemChat.setText("Digite seu texto aqui...");
		txtMinhaMensagemChat.setBounds(346, 361, 200, 23);
		frame.getContentPane().add(txtMinhaMensagemChat);
		txtMinhaMensagemChat.setColumns(10);
		
		txtNomeDeUsuario = new JTextField();
		txtNomeDeUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		txtNomeDeUsuario.setMargin(new Insets(2, 3, 2, 3));
		txtNomeDeUsuario.setColumns(10);
		txtNomeDeUsuario.setBounds(612, 133, 200, 23);
		frame.getContentPane().add(txtNomeDeUsuario);
		
		JLabel lblInfoSobreNomeDeUsuario = new JLabel("Nome de usu\u00E1rio");
		lblInfoSobreNomeDeUsuario.setForeground(new Color(0, 128, 0));
		lblInfoSobreNomeDeUsuario.setBackground(new Color(255, 240, 245));
		lblInfoSobreNomeDeUsuario.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblInfoSobreNomeDeUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoSobreNomeDeUsuario.setBounds(640, 167, 150, 14);
		frame.getContentPane().add(lblInfoSobreNomeDeUsuario);
		
		JLabel lblConfirmNomeUsuario = new JLabel("Aguardando confirma\u00E7\u00E3o...");
		lblConfirmNomeUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfirmNomeUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblConfirmNomeUsuario.setBounds(566, 110, 289, 20);
		frame.getContentPane().add(lblConfirmNomeUsuario);
		
		JButton btnNovaSala = new JButton("Nova sala");
		btnNovaSala.setBackground(new Color(102, 205, 170));
		btnNovaSala.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtNomeDeUsuario.getText().isBlank()) {
					lblConfirmNomeUsuario.setText("Informe seu nome!");
					lblConfirmNomeUsuario.setForeground(new Color(200, 0, 0));
				}
				else if (!textNomeDaNovaSala.getText().isBlank()) {
					meuNome = txtNomeDeUsuario.getText();
					criaNovaSala(textNomeDaNovaSala.getText(), lblIndicaSalaAtual);
					lblConfirmNovaSala.setText("Sucesso! Sala: " + textNomeDaNovaSala.getText());
					lblConfirmNovaSala.setForeground(new Color(0, 200, 0));					
					lblConfirmNomeUsuario.setText("");
					recebeMensagensDoChat(); // Começa a receber mensagem de todos
					recebeMensagensDoChatPrivadas(); // Começa a receber mensagem particular
					textAreaChat.setText("");
				} else {
					meuNome = txtNomeDeUsuario.getText();
					lblConfirmNovaSala.setText("Vazio! Inválido!");
					lblConfirmNovaSala.setForeground(new Color(200, 0, 0));					
					lblConfirmNomeUsuario.setText("");
				}
			}
		});
		btnNovaSala.setBounds(25, 24, 125, 23);
		frame.getContentPane().add(btnNovaSala);
		
		JButton btnEntrarNaSala = new JButton("Entrar na sala");
		btnEntrarNaSala.setBackground(new Color(152, 251, 152));
		btnEntrarNaSala.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtNomeDeUsuario.getText().isBlank()) {
					lblConfirmNomeUsuario.setText("Informe seu nome!");
					lblConfirmNomeUsuario.setForeground(new Color(200, 0, 0));
				}
				else if (comboBoxListaDeSalas.getSelectedItem() != null && !comboBoxListaDeSalas.getSelectedItem().toString().isBlank()) {
					meuNome = txtNomeDeUsuario.getText();
					entraNaSala(comboBoxListaDeSalas.getSelectedItem().toString(), lblIndicaSalaAtual);
					lblConfirmSalaExistente.setText("Sucesso! Sala: " + comboBoxListaDeSalas.getSelectedItem().toString());
					lblConfirmSalaExistente.setForeground(new Color(0, 200, 0));					
					lblConfirmNomeUsuario.setText("");
					recebeMensagensDoChat(); // Começa a receber mensagem de todos
					recebeMensagensDoChatPrivadas(); // Começa a receber mensagem particular
					textAreaChat.setText("");
				} else {
					meuNome = txtNomeDeUsuario.getText();
					lblConfirmSalaExistente.setText("Vazio! Inválido!");
					lblConfirmSalaExistente.setForeground(new Color(200, 0, 0));					
					lblConfirmNomeUsuario.setText("");
				}
			}
		});
		btnEntrarNaSala.setBounds(25, 70, 125, 23);
		frame.getContentPane().add(btnEntrarNaSala);
		
		JButton btnEnviarTodos = new JButton("Enviar para todos");
		btnEnviarTodos.setBackground(new Color(30, 144, 255));
		btnEnviarTodos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				enviaMensagemParaTodos();
			}
		});
		btnEnviarTodos.setBounds(167, 360, 150, 25);
		frame.getContentPane().add(btnEnviarTodos);
		
		JButton btnEnviarParticular = new JButton("Enviar para :");
		btnEnviarParticular.setBackground(new Color(192, 192, 192));
		btnEnviarParticular.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				enviaMensagemPrivada(comboBoxListaDePessoas);
			}
		});
		btnEnviarParticular.setBounds(167, 400, 150, 25);
		frame.getContentPane().add(btnEnviarParticular);
		
		JButton btnAtualizarListaDeSalas = new JButton("Atualizar lista");
		btnAtualizarListaDeSalas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				atualizaListaDeSalas(comboBoxListaDeSalas);
			}
		});
		btnAtualizarListaDeSalas.setBackground(new Color(230, 230, 250));
		btnAtualizarListaDeSalas.setBounds(25, 115, 125, 23);
		frame.getContentPane().add(btnAtualizarListaDeSalas);
		
		JButton btnAtualizarListaDePessoas = new JButton("Atualizar");
		btnAtualizarListaDePessoas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				atualizaListaDePessoas(textAreaPessoas, comboBoxListaDePessoas);
			}
		});
		btnAtualizarListaDePessoas.setBackground(new Color(230, 230, 250));
		btnAtualizarListaDePessoas.setBounds(636, 360, 125, 23);
		frame.getContentPane().add(btnAtualizarListaDePessoas);
		
	}
	
	
	public void procuraPeloSevicoJavaSpace () {
		System.out.println("Procurando pelo serviço JavaSpace...");
		finder = new Lookup(JavaSpace.class);
		space = (JavaSpace) finder.getService();
		if (space == null) {
			System.out.println("O serviço JavaSpace não foi encontrado. Encerrando...");
			System.exit(-1);
		}			
		System.out.println("O serviço JavaSpace foi encontrado.");
		return;
	}
	
	
	public void criaNovaSala (String nomeNovaSala, JLabel lblMinhaSalaAtual) {
		ArrayList<String> listSalas = new ArrayList<>();
		SalaDeChat template = new SalaDeChat();
		IndiceUltimaMensagem indUltMsg = new IndiceUltimaMensagem();
		
		try {
			// Pega nome das salas existentes
			while (true) {
				SalaDeChat sala = (SalaDeChat) space.take(template, null, 0);
				if (sala == null) {
					break;
				}
				listSalas.add(sala.nomeDaSala);
				System.out.print(sala.nomeDaSala + " | ");
			}
			System.out.println(" <- Salas existentes");
			
			if (!listSalas.contains(nomeNovaSala)) {
				// Remove o nome da lista de alguma sala caso exista
				UsuarioNaSala usuNaSal = new UsuarioNaSala();
				if (minhaSala != null) {
					System.out.println("Sala não nula: " + minhaSala + ". Meu nome: " + meuNome);
					usuNaSal.nomeDaSala = minhaSala;
					usuNaSal.nomeDoUsuario = meuNome;
					UsuarioNaSala meuUsuAntigSal = (UsuarioNaSala) space.take(usuNaSal, null, 0);
					if (meuUsuAntigSal != null) {
						System.out.println("Eu " + meuUsuAntigSal.nomeDoUsuario + " Removido da: " + meuUsuAntigSal.nomeDaSala);
					} else {
						System.out.println("Sala não estava no espaço.");
					}
				} else {
					System.out.println("Você não estava em nenhuma sala.");
				}
				
				// Cria nova sala com o nome 'nomeNovaSala'
				template.nomeDaSala = nomeNovaSala;
				space.write(template, null, Long.MAX_VALUE);
				minhaSala = nomeNovaSala;
				lblMinhaSalaAtual.setText(nomeNovaSala);
				System.out.println("Minha sala criada: " + minhaSala);
				
				// Cria nova tupla para contar as mensagens da sala
				indUltMsg.nomeDaSala = nomeNovaSala;
				indUltMsg.indiceUltimaMensagem = 0;
				space.write(indUltMsg, null, Long.MAX_VALUE);
				indiceDaMensagemNaHoraQueEntrei = 0;
				quantMsgLidasEnviadasAposEntrarNaSala = 0;
				quantMsgPrivadasRecebidas = 0;
				
				// Insire meu nova na "lista" de tuplas dos usuários da sala criada				
				usuNaSal.nomeDaSala = minhaSala;
				usuNaSal.nomeDoUsuario = meuNome;
				System.out.println("Usu.Sala: " + usuNaSal.nomeDaSala + " Usu.nome: " + usuNaSal.nomeDoUsuario);
				space.write(usuNaSal, null, Long.MAX_VALUE);
			}
			while (!listSalas.isEmpty()) {
				template.nomeDaSala = listSalas.get(listSalas.size() - 1);				
				space.write(template, null, Long.MAX_VALUE);
				listSalas.remove(listSalas.size() - 1);
				System.out.print(template.nomeDaSala + " | ");
			}
			System.out.println(" <- Salas 'devolvidas'");			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	public void entraNaSala (String nomeNovaSala, JLabel lblMinhaSalaAtual) {
		UsuarioNaSala usuNaSal = new UsuarioNaSala();
		try {
			// Remove o nome da lista de alguma sala caso exista			
			if (minhaSala != null) {
				usuNaSal.nomeDaSala = minhaSala;
				usuNaSal.nomeDoUsuario = meuNome;
				UsuarioNaSala meuUsuAntigSal = (UsuarioNaSala) space.take(usuNaSal, null, 0);
				if (meuUsuAntigSal != null) {
					System.out.println("Eu " + meuUsuAntigSal.nomeDoUsuario + " Removido da: " + meuUsuAntigSal.nomeDaSala);
				} else {
					System.out.println("Sala não estava no espaço.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		System.out.println(" -- Sala a entrar: " + nomeNovaSala);
		minhaSala = nomeNovaSala;
		lblMinhaSalaAtual.setText(nomeNovaSala);
				
		IndiceUltimaMensagem indUltMsg = new IndiceUltimaMensagem();
		indUltMsg.nomeDaSala = minhaSala;
		
		try {
			// Insere meu nova na "lista" de tuplas dos usuários da sala criada				
			usuNaSal.nomeDaSala = minhaSala;
			usuNaSal.nomeDoUsuario = meuNome;
			space.write(usuNaSal, null, Long.MAX_VALUE);
			
			// Pega o indice da ultima mensagem no momento que entrou no chat
			IndiceUltimaMensagem indUltMsgSalaAtual = (IndiceUltimaMensagem) space.take(indUltMsg, null, 60 * 1000);
			System.out.println("IndUltMsg: " + indUltMsgSalaAtual.nomeDaSala + " " + indUltMsgSalaAtual.indiceUltimaMensagem);
			indiceDaMensagemNaHoraQueEntrei = indUltMsgSalaAtual.indiceUltimaMensagem;
			quantMsgLidasEnviadasAposEntrarNaSala = 0;
			quantMsgPrivadasRecebidas = 0;
			space.write(indUltMsgSalaAtual, null, Long.MAX_VALUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
	
	public void atualizaListaDeSalas (JComboBox cmbBListSalas) {
		ArrayList<String> listSalas = new ArrayList<>();
		SalaDeChat template = new SalaDeChat();
		
		cmbBListSalas.removeAllItems();
		
		// Pega nome das salas existentes
		try {
			while (true) {
				SalaDeChat sala = (SalaDeChat) space.take(template, null, 0);
				if (sala == null) {
					break;
				}
				listSalas.add(sala.nomeDaSala);
				cmbBListSalas.addItem(sala.nomeDaSala);
				System.out.print(sala.nomeDaSala + " | ");
			}
			System.out.println(" <- Lista atualizada");
						
			while (!listSalas.isEmpty()) {
				template.nomeDaSala = listSalas.get(listSalas.size() - 1);				
				space.write(template, null, Long.MAX_VALUE);
				listSalas.remove(listSalas.size() - 1);
				System.out.print(template.nomeDaSala + " | ");
			}
			System.out.println(" <- Salas 'devolvidas'");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	public void enviaMensagemParaTodos () {
		if (minhaSala == null) return;
		
		Mensagem minhaMensagemParaTodos = new Mensagem();
		IndiceUltimaMensagem IndUltMsg = new IndiceUltimaMensagem();
		IndUltMsg.nomeDaSala = minhaSala;
		
		try {
			minhaMensagemParaTodos.nomeDaSala = minhaSala;
			minhaMensagemParaTodos.nomeDoUsuario = "Todos";
			minhaMensagemParaTodos.indiceDaMensagem = indiceDaMensagemNaHoraQueEntrei + quantMsgLidasEnviadasAposEntrarNaSala + 1;
			if (txtMinhaMensagemChat.getText().isBlank()) {
				minhaMensagemParaTodos.msg = meuNome + ": ...";
			}
			else {
				minhaMensagemParaTodos.msg = meuNome + ": " + txtMinhaMensagemChat.getText();
			}
			space.write(minhaMensagemParaTodos, null, Long.MAX_VALUE);
			// textAreaChat.setText(textAreaChat.getText() + minhaMensagemParaTodos.msg + "\r\n");
			/*
			 * Atualizar no espaço o indice da mensagem
			 * */
			IndiceUltimaMensagem indUltMsgSala = (IndiceUltimaMensagem) space.take(IndUltMsg, null, 60 * 1000);			
			indUltMsgSala.indiceUltimaMensagem += 1;
			space.write(indUltMsgSala, null, Long.MAX_VALUE);
			/*
			 * Atualizar no espaço o indice da mensagem
			 * */
			txtMinhaMensagemChat.setText("");
			System.out.println("Enviado em Todos: Sala: " + minhaMensagemParaTodos.nomeDaSala + " Usuario: " + minhaMensagemParaTodos.nomeDoUsuario +
							   " Msg: " + minhaMensagemParaTodos.msg +  " IndcMsg: " + minhaMensagemParaTodos.indiceDaMensagem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return;
	}
	
	
	public void enviaMensagemPrivada (JComboBox cmbBListSalas) {
		if (minhaSala == null) return;
		
		if (cmbBListSalas.getSelectedItem() == null || cmbBListSalas.getSelectedItem().toString().isBlank()) {
			System.out.println("Ninguém selecionado");
			return;
		}
		
		Mensagem minhaMensagemParticular = new Mensagem();
		
		try {
			minhaMensagemParticular.nomeDaSala = minhaSala;
			minhaMensagemParticular.nomeDoUsuario = cmbBListSalas.getSelectedItem().toString();
			minhaMensagemParticular.indiceDaMensagem = indiceDaMensagemNaHoraQueEntrei + quantMsgPrivadasRecebidas++ + 1;
			if (txtMinhaMensagemChat.getText().isBlank()) {
				minhaMensagemParticular.msg = "...";
			}
			else {
				minhaMensagemParticular.msg = meuNome + " (Privado): " + txtMinhaMensagemChat.getText();
			}
			space.write(minhaMensagemParticular, null, Long.MAX_VALUE);
			/*
			 * Atualizar no espaço o indice da mensagem
			 * */
			txtMinhaMensagemChat.setText("");
			System.out.println("Msg Priv Enviada. Sala: " + minhaMensagemParticular.nomeDaSala + " Usuario: " + minhaMensagemParticular.nomeDoUsuario +
					   " Msg: " + minhaMensagemParticular.msg +  " IndcMsg: " + minhaMensagemParticular.indiceDaMensagem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
	
	public void atualizaListaDePessoas (JTextArea textAreaListaDePessoas, JComboBox comboBoxListPessoasNaSala) {
		if (minhaSala == null) return;
		
		ArrayList<String> listPessoas = new ArrayList<>();
		UsuarioNaSala usuNaSala = new UsuarioNaSala();
		usuNaSala.nomeDaSala = minhaSala;
		textAreaListaDePessoas.setText("");
		comboBoxListPessoasNaSala.removeAllItems();
		
		try {
			while (true) {
				UsuarioNaSala usuNaMinhaSalaDeChat = (UsuarioNaSala) space.take(usuNaSala, null, 0);
				if (usuNaMinhaSalaDeChat == null) {
					break;
				}
				listPessoas.add(usuNaMinhaSalaDeChat.nomeDoUsuario);
				textAreaListaDePessoas.setText(textAreaListaDePessoas.getText() + usuNaMinhaSalaDeChat.nomeDoUsuario + "\r\n");
				comboBoxListPessoasNaSala.addItem(usuNaMinhaSalaDeChat.nomeDoUsuario);
				System.out.print(usuNaMinhaSalaDeChat.nomeDoUsuario + " | ");
			}
			System.out.println(" <- Pessoas na sala");
			
			while (!listPessoas.isEmpty()) {
				usuNaSala.nomeDoUsuario = listPessoas.get(listPessoas.size() - 1);				
				space.write(usuNaSala, null, Long.MAX_VALUE);
				listPessoas.remove(listPessoas.size() - 1);
				System.out.print(usuNaSala.nomeDoUsuario + " | ");
			}
			System.out.println(" <- Pessoas 'devolvidas' para " + minhaSala);

		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return;
	}
	
	
	public void recebeMensagensDoChat () {
		Mensagem msgChatTodos = new Mensagem();
		msgChatTodos.nomeDaSala = minhaSala;
		msgChatTodos.nomeDoUsuario = "Todos";
		msgChatTodos.indiceDaMensagem = indiceDaMensagemNaHoraQueEntrei  + quantMsgLidasEnviadasAposEntrarNaSala;
		
		recebeMsg = new Thread() {
			@Override
			public void run () {
				Mensagem msgChatTodosNovaMsg;
				System.out.println("Recebendo mensagens...");
				while (!recebeMsgFim) {
					try {
						msgChatTodos.indiceDaMensagem = indiceDaMensagemNaHoraQueEntrei  + quantMsgLidasEnviadasAposEntrarNaSala + 1;
						msgChatTodosNovaMsg = (Mensagem) space.read(msgChatTodos, null, 1 * 1000);
						if (msgChatTodosNovaMsg == null) {
							// System.out.println("msgChatTodosNovaMsg Null!");
							sleep(500);
						} else {							
							textAreaChat.setText(textAreaChat.getText() + msgChatTodosNovaMsg.msg + "\r\n");
							quantMsgLidasEnviadasAposEntrarNaSala++;
							System.out.println("Recebido em todos: " + msgChatTodosNovaMsg.msg);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}				
			}
		};
		
		recebeMsg.start();
	}
	
	public void recebeMensagensDoChatPrivadas () {
		Mensagem msgChatPrivada = new Mensagem();
		msgChatPrivada.nomeDaSala = minhaSala;
		msgChatPrivada.nomeDoUsuario = meuNome;
		msgChatPrivada.indiceDaMensagem = indiceDaMensagemNaHoraQueEntrei  + quantMsgLidasEnviadasAposEntrarNaSala;
		
		recebeMsgPriv = new Thread() {			
			@Override
			public void run () {
				Mensagem msgChatPrivNovaMsg;
				System.out.println("Recebendo mensagens privadas...");
				while (!recebeMsgPrivFim) {					
					try {
						msgChatPrivada.indiceDaMensagem = indiceDaMensagemNaHoraQueEntrei + quantMsgPrivadasRecebidas + 1;
						msgChatPrivNovaMsg = (Mensagem) space.take(msgChatPrivada, null, 1 * 1000);
						if (msgChatPrivNovaMsg == null) {
							sleep(500);
						} else {							
							textAreaChat.setText(textAreaChat.getText() + msgChatPrivNovaMsg.msg + "\r\n");
							quantMsgPrivadasRecebidas++;
							System.out.println("Recebido em privado: " + msgChatPrivNovaMsg.msg);
						}						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}				
			}
		};
		
		recebeMsgPriv.start();
	}
}
