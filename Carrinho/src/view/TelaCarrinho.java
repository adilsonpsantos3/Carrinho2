

/** 
 *   Authors @author igor.jsantos10 @autor Adilson.pinheiro @author Jerry Ferreira
 */

package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;

public class TelaCarrinho extends JFrame {

	private JPanel contentPane;
	private JLabel lblStatus;
	private JTextField txtcodigo;
	private JTextField txtproduto;
	private JTextField txtquantidade;
	private JTextField txtvalor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCarrinho frame = new TelaCarrinho();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaCarrinho() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {

				// Ativação do formulário
				// Status da conexão

				status();
			}

		});
		setTitle("Tela Carrinho");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaCarrinho.class.getResource("/icones/favicon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 393);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(TelaCarrinho.class.getResource("/icones/dberror.png")));
		lblStatus.setBounds(370, 179, 46, 48);
		contentPane.add(lblStatus);

		JLabel lblNewLabel = new JLabel("C\u00F3digo");
		lblNewLabel.setBounds(10, 11, 46, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Produto");
		lblNewLabel_1.setBounds(10, 48, 46, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("QTD");
		lblNewLabel_2.setBounds(10, 82, 46, 14);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Valor");
		lblNewLabel_3.setBounds(10, 130, 46, 14);
		contentPane.add(lblNewLabel_3);

		txtcodigo = new JTextField();
		
		txtcodigo.setBounds(54, 8, 86, 20);
		contentPane.add(txtcodigo);
		txtcodigo.setColumns(10);

		txtproduto = new JTextField();
		txtproduto.setEnabled(false);
		txtproduto.setBounds(54, 45, 227, 20);
		contentPane.add(txtproduto);
		txtproduto.setColumns(10);

		txtquantidade = new JTextField();
		txtquantidade.setBounds(54, 79, 115, 20);
		contentPane.add(txtquantidade);
		txtquantidade.setColumns(10);

		txtvalor = new JTextField();
		txtvalor.setBounds(54, 127, 227, 20);
		contentPane.add(txtvalor);
		txtvalor.setColumns(10);

		btnRead = new JButton("");
		btnRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionarContato();

			}
		});
		btnRead.setEnabled(false);
		btnRead.setToolTipText("Buscar contato");
		btnRead.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRead.setIcon(new ImageIcon(TelaCarrinho.class.getResource("/icones/read.png")));
		btnRead.setBounds(327, 30, 48, 48);
		contentPane.add(btnRead);

		btnCreate = new JButton("");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inserirContato();
			}
		});
		btnCreate.setEnabled(false);
		btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreate.setBorder(null);
		btnCreate.setBackground(SystemColor.control);
		btnCreate.setToolTipText("Adicionar Produto");
		btnCreate.setIcon(new ImageIcon(TelaCarrinho.class.getResource("/icones/create.png")));
		btnCreate.setBounds(44, 175, 64, 64);
		contentPane.add(btnCreate);

		btnUpdate = new JButton("");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarproduto();
			}
		});
		btnUpdate.setEnabled(false);
		btnUpdate.setToolTipText("Atualizar produto");
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setIcon(new ImageIcon(TelaCarrinho.class.getResource("/icones/update.png")));
		btnUpdate.setBorder(null);
		btnUpdate.setBackground(SystemColor.control);
		btnUpdate.setBounds(145, 175, 64, 64);
		contentPane.add(btnUpdate);

		BtnDelete = new JButton("");
		BtnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletarProduto();	}
		});
		BtnDelete.setEnabled(false);
		BtnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		BtnDelete.setToolTipText("Apagar produto");
		BtnDelete.setBorder(null);
		BtnDelete.setBackground(SystemColor.control);
		BtnDelete.setIcon(new ImageIcon(TelaCarrinho.class.getResource("/icones/delete.png")));
		BtnDelete.setBounds(246, 175, 64, 64);
		contentPane.add(BtnDelete);
	} // FIM DO CONSTRUTOR

	// Criação de um objeto para acessar o método da classe DAO
	DAO dao = new DAO();
	private JButton btnRead;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton BtnDelete;

	/**
	 * Status da conexão
	 *
	 */
	private void status() {
		try {
			// Estabelecer uma conexão
			Connection con = dao.conectar();
			// status
			// System.out.println(con);
			// Trocando o icone do DBA
			if (con != null) {
				lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dbok.png")));
				btnRead.setEnabled(true);
				
			} else {
				lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dberror.png")));

			}
			// Encerrar conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);

		}

	}

	
	private void selecionarContato() {
		// Instrução sql para pesquisar um produto
		String read = "select * from carrinho where codigo = ?";
		try {

			
			Connection con = dao.conectar();
			
			PreparedStatement pst = con.prepareStatement(read);
			
			pst.setString(1, txtcodigo.getText());
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				txtcodigo.setText(rs.getString(1)); 
				txtproduto.setText(rs.getString(2)); 
				txtquantidade.setText(rs.getString(3)); 
				txtvalor.setText(rs.getString(4)); 
				btnUpdate.setEnabled(true);
				BtnDelete.setEnabled(true);
				btnRead.setEnabled(false);
				txtproduto.setEnabled(true);
				
			} else {
				// Criar uma caixa de MSG no java
				JOptionPane.showMessageDialog(null, "Produto inexistente");
				txtproduto.setEnabled(true);
				btnCreate.setEnabled(true);

			}

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * Inserir um novo produto CRUD Create
	 */

	private void inserirContato() {
		// Validação dos campos
		if (txtproduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Nome do produto obrigatório");
		}else if (txtquantidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Quantidade obrigatória");
		}else if (txtproduto.getText().length() > 50 ){
			JOptionPane.showMessageDialog(null, "O campo não pode ter mais que 50 caracteres");
		}else if (txtquantidade.getText().length() > 15 ){
			JOptionPane.showMessageDialog(null, "O campo não pode ter mais que 15 caracteres");
		}else if (txtvalor.getText().length() > 50 ){
			JOptionPane.showMessageDialog(null, "O campo não pode ter mais que 50 caracteres");
			
			
		
		} else {
			String create = "insert into carrinho (produto, quantidade, valor) values (?,?,?)";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(create);
				pst.setString(1, txtproduto.getText());
				pst.setString(2, txtquantidade.getText());
				pst.setString(3, txtvalor.getText());
				//Executar Query (Insert no Banco de dados)
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Produto adicionado");
				con.close();
				limpar();
			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	/**
	 * Editar produto CRUD UPDATE
	 */
	private void alterarproduto() {
		if (txtproduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha com o nome do contato");
		} else if (txtvalor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha com o telefone do contato");
		} //else if (txtquantidade.getText().isEmpty()) {
			//JOptionPane.showMessageDialog(null, "Preencha com o telefone do contato");
	
			String update = "update carrinho set produto=?,quantidade=?,valor=? where codigo=?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(update);
				pst.setString(1, txtproduto.getText());
				pst.setString(2, txtquantidade.getText());
				pst.setString(3, txtvalor.getText());
				pst.setString(4, txtcodigo.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Produto atualizado");
				con.close();
				limpar();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	

	/**
	 * Excluir produto CRUD Delete
	 */
	private void deletarProduto() {
		String delete = "delete from carrinho where codigo=?";
		// Confirmação de exclusão do produto
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste produto?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtcodigo.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Produto excluido com sucesso");
				limpar();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			limpar();
		}

	}
	
	/**
	 * Limpar campos e configurar botões
	 */
	private void limpar() {
		txtcodigo.setText(null);
		txtproduto.setText(null);
		txtquantidade.setText(null);
		txtvalor.setText(null);
		btnCreate.setEnabled(false);
		btnUpdate.setEnabled(false);
		BtnDelete.setEnabled(false);
		btnRead.setEnabled(true);
		
		
	}
}
