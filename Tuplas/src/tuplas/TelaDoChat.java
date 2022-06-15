package tuplas;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

public class TelaDoChat {

	private JFrame frame;
	private JTextField textNomeDaNovaSala;

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
		
		JButton btnNovaSala = new JButton("Nova sala");
		btnNovaSala.setBackground(new Color(102, 205, 170));
		btnNovaSala.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnNovaSala.setBounds(25, 24, 125, 23);
		frame.getContentPane().add(btnNovaSala);
		
		JButton btnEntrarNaSala = new JButton("Entrar na sala");
		btnEntrarNaSala.setBackground(new Color(152, 251, 152));
		btnEntrarNaSala.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnEntrarNaSala.setBounds(25, 70, 125, 23);
		frame.getContentPane().add(btnEntrarNaSala);
		
		JComboBox comboBoxListaDeSalas = new JComboBox();
		comboBoxListaDeSalas.setMaximumRowCount(4);
		comboBoxListaDeSalas.setBounds(197, 70, 352, 25);		
		comboBoxListaDeSalas.addItem("item 1");
		comboBoxListaDeSalas.addItem("item 2");
		comboBoxListaDeSalas.addItem("item 3");
		comboBoxListaDeSalas.addItem("item 4");
		comboBoxListaDeSalas.addItem("item 5");
		comboBoxListaDeSalas.addItem("item 6");
		comboBoxListaDeSalas.addItem("item 7");
		comboBoxListaDeSalas.addItem("item 8");
		frame.getContentPane().add(comboBoxListaDeSalas);
		comboBoxListaDeSalas.removeItem("item 7");
		
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
		scrollPane.setBounds(166, 250, 378, 91);
		frame.getContentPane().add(scrollPane);
		
		JTextArea textAreaChat = new JTextArea();
		textAreaChat.setEditable(false);
		textAreaChat.setLineWrap(true);
		textAreaChat.setMargin(new Insets(3, 3, 3, 3));
		scrollPane.setViewportView(textAreaChat);
		
		JScrollPane scrollPane_1 = new JScrollPane();
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
		
		JButton btnEnviarTodos = new JButton("Enviar para todos");
		btnEnviarTodos.setBackground(new Color(30, 144, 255));
		btnEnviarTodos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnEnviarTodos.setBounds(167, 360, 150, 25);
		frame.getContentPane().add(btnEnviarTodos);
		
		JButton btnEnviarParticular = new JButton("Enviar para :");
		btnEnviarParticular.setBackground(new Color(192, 192, 192));
		btnEnviarParticular.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnEnviarParticular.setBounds(167, 400, 150, 25);
		frame.getContentPane().add(btnEnviarParticular);
		
		JLabel lblNewLabel = new JLabel("Sala");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(26, 260, 100, 20);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Sala");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(new Color(255, 69, 0));
		lblNewLabel_1.setBounds(26, 290, 100, 20);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnAtualizarListaDeSalas = new JButton("Atualizar lista");
		btnAtualizarListaDeSalas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnAtualizarListaDeSalas.setBackground(new Color(230, 230, 250));
		btnAtualizarListaDeSalas.setBounds(25, 115, 125, 23);
		frame.getContentPane().add(btnAtualizarListaDeSalas);
		
		JButton btnAtualizarListaDePessoas = new JButton("Atualizar");
		btnAtualizarListaDePessoas.setBackground(new Color(230, 230, 250));
		btnAtualizarListaDePessoas.setBounds(636, 360, 125, 23);
		frame.getContentPane().add(btnAtualizarListaDePessoas);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setMaximumRowCount(1);
		comboBox.setBounds(346, 400, 200, 24);
		frame.getContentPane().add(comboBox);
		
		//SwingUtilities.updateComponentTreeUI(frame);
	}
}
