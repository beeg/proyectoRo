package Client;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import Util.SocketManager;

/**
 * @author Aritz Ventana con la que se creará un nuevo usuario.
 */
public class VentanaLogin extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextField nick;
	private JPasswordField password;
	private JTextField ip;
	private JButton bLogin;
	private JButton bCancelar;
	private JLabel mensajes;
	private Color green;
	private Color red;
	private Cliente c;
	private SocketManager sm;

	/**
	 * @param pvL
	 *            Ventana de donde es llamado
	 * @param sU
	 *            Sistema Usuarios para poder insertar el nuevo usuario
	 */
	public VentanaLogin() {
		// Creacion de Componentes
		mensajes=new JLabel();
		green=new Color(0,255,0);
		red=new Color(255,0,0);
		nick = new JTextField(12);
		nick.setEnabled(true);
		nick.setColumns(15);
		password = new JPasswordField(20);
		password.setColumns(15);
		password.setEnabled(true);
		ip = new JTextField(12);
		ip.setEnabled(true);
		ip.setColumns(15);
		JLabel usuario = new JLabel("Usuario: ");
		JLabel contraseña = new JLabel("Contraseña: ");
		JLabel contraseña2 = new JLabel("IP: ");
		bLogin = new JButton("Conectar");
		bLogin.addActionListener(this);
		bCancelar = new JButton("Cancelar");
		bCancelar.addActionListener(this);

		// Creacion de panel
		Container panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(10, 10, 10, 10);
		gc.gridx = 1;
		gc.gridy = 1;
		panel.add(usuario, gc);
		gc.gridx = 2;
		panel.add(nick, gc);
		gc.gridx = 1;
		gc.gridy = 2;
		panel.add(contraseña, gc);
		gc.gridx = 2;
		panel.add(password, gc);
		gc.gridx = 1;
		gc.gridy = 3;
		panel.add(contraseña2, gc);
		gc.gridx = 2;
		panel.add(ip, gc);
		gc.gridx = 1;
		gc.gridy = 4;
		panel.add(bLogin, gc);
		gc.gridx = 2;
		panel.add(bCancelar, gc);
		gc.gridx = 1;
		gc.gridy = 5;

		// Poner el fondo y los bordes de color.
		panel.setBackground(Color.GRAY);
		((JComponent) panel).setBorder(new LineBorder(Color.BLACK));
		Container principal=new JPanel();
		principal.setLayout(new BoxLayout(principal,BoxLayout.Y_AXIS));
		principal.add(panel);
		principal.add(mensajes);
		this.getContentPane().add(principal);
		// Botón predeterminado, resizable,título
		this.getRootPane().setDefaultButton(bLogin);
		this.setResizable(true);
		this.setVisible(true);
		this.setTitle("Inicio de la conexión:");
		// Para cuando le da a la x se comporte como si fuese el boton cancelar
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(c!=null){
					try {
						c.SALIR();
					} catch (IOException e1) {
						System.out.println("Error al Salir");
						e1.printStackTrace();
					}
				}
				dispose();
			}
		});
		// Para que aparezca en el centro al darle a crear cuenta
		this.setBounds(486, 220, 400, 235);

	}

	@Override
	public void actionPerformed(ActionEvent a) {
		Object o = a.getSource();
		if (o == bLogin) {
			String mensaje;
			try{
				if(c==null)	{
					sm = new SocketManager(ip.getText(), 5888);
					c=new Cliente(sm);
				}
				mensaje=c.userLogin(this.nick.getText());
			if(mensaje.contains("201")){
				mensajes.setBackground(green);
				mensajes.setText(mensaje);
				mensaje=c.passLogin(this.password.getText());
				if(mensaje.contains("202")){
					VentanaMenu vm=new VentanaMenu(c);
					vm.setVisible(true);
					this.dispose();
				}else{
					JOptionPane.showMessageDialog(this,mensaje, "Error al logear",JOptionPane.ERROR_MESSAGE);
				}
			}else{
				mensajes.setBackground(red);
				mensajes.setText(mensaje);
				JOptionPane.showMessageDialog(this,mensaje, "Error al logear",JOptionPane.ERROR_MESSAGE);
			}
			}catch(IOException e){
				System.out.println("Excepcion b login");
			}
		} else {
			if (o == bCancelar) {
				if(c!=null){
					try {
						c.SALIR();
					} catch (IOException e) {
						System.out.println("Error al salir");
						e.printStackTrace();
					}
				}
				this.dispose();
			}
		}

	}

	public static void main(String[] args) {
		VentanaLogin vC = new VentanaLogin();
		vC.setVisible(true);

	}
}
