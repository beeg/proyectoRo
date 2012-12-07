package Server;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import DB.GestorBD;

public class VentanaAdministrador extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton bCrearUsuario;
	private JButton bBorrarUsuario;
	private JButton bModificarUsuario;
	private JButton bDesconectar;
	@SuppressWarnings("rawtypes")
	private JList listaUsuariosON;
	@SuppressWarnings("rawtypes")
	private JComboBox cbUsuarios;
	private JTextField nombreU;
	private JTextField contraseña;
	private JTextField nombreTipoUsuario;
	private JButton bCambiarLim;
	private JTextField numUsuariosMax;
	private JLabel numUsuariosActuales;
	private Vehiculo v;
	private ArrayList<Usuario> arrayUsuarios;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public VentanaAdministrador(final Vehiculo v) {
		this.v = v;
		// Creacion de componentes
		bCrearUsuario = new JButton("Crear");
		bCrearUsuario.addActionListener(this);
		bBorrarUsuario = new JButton("Borrar");
		bBorrarUsuario.addActionListener(this);
		bModificarUsuario = new JButton("Modificar");
		bModificarUsuario.addActionListener(this);
		listaUsuariosON = new JList();
		nombreU = new JTextField(15);
		contraseña = new JTextField(15);
		nombreTipoUsuario = new JTextField(15);
		nombreTipoUsuario.setEditable(false);
		arrayUsuarios = v.getlUsuarios();
		cbUsuarios = new JComboBox();
		cbUsuarios.setModel(new DefaultComboBoxModel(arrayUsuarios.toArray()));
		cbUsuarios.addActionListener(this);
		bDesconectar = new JButton("Desconectar");
		bDesconectar.addActionListener(this);
		bCambiarLim = new JButton("Cambiar límite");
		bCambiarLim.addActionListener(this);
		numUsuariosActuales = new JLabel(v.getNumActualUsuarios() + "");
		numUsuariosMax = new JTextField(15);
		numUsuariosMax.setText(v.getNumMaxUsuarios() + "");

		// Creación de la botonera
		Container cBotonera = new JPanel();
		cBotonera.setLayout(new FlowLayout(FlowLayout.LEFT));
		cBotonera.add(bCrearUsuario);
		cBotonera.add(bBorrarUsuario);
		cBotonera.add(bModificarUsuario);
		cBotonera.add(bDesconectar);
		cBotonera.add(cbUsuarios);

		// Creacion del panel con la lista de usuarios conectados
		Container cListaUsuarios = new JPanel();
		cListaUsuarios.setLayout(new FlowLayout(FlowLayout.LEFT));
		JScrollPane scListaUsu = new JScrollPane(listaUsuariosON);
		scListaUsu.setPreferredSize(new Dimension(150, 200));
		cListaUsuarios.add(scListaUsu);

		// Creacion con los datos de usuario
		JLabel nickU = new JLabel("Nickname");
		JLabel passU = new JLabel("Password");
		Container panelDatos = new JPanel();
		panelDatos.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(10, 10, 10, 10);
		gc.gridx = 1;
		gc.gridy = 1;
		panelDatos.add(nickU, gc);
		gc.gridx = 2;
		panelDatos.add(nombreU, gc);
		gc.gridx = 1;
		gc.gridy = 2;
		panelDatos.add(passU, gc);
		gc.gridx = 2;
		panelDatos.add(contraseña, gc);

		// Parte para el limite de usuarios en el servidor
		JLabel limUsu = new JLabel("Limite de usuarios: ");
		JLabel usuAct = new JLabel("Usuarios actuales: ");
		Container cLimiteUsuarios = new JPanel();
		cLimiteUsuarios.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.insets = new Insets(10, 10, 10, 10);
		gc.gridx = 1;
		gc.gridy = 1;
		cLimiteUsuarios.add(limUsu, gc);
		gc.gridx = 2;
		cLimiteUsuarios.add(numUsuariosMax, gc);
		gc.gridx = 1;
		gc.gridy = 2;
		cLimiteUsuarios.add(usuAct, gc);
		gc.gridx = 2;
		cLimiteUsuarios.add(numUsuariosActuales, gc);
		gc.gridx = 1;
		gc.gridy = 3;
		cLimiteUsuarios.add(bCambiarLim, gc);

		/*
		 * Creacion panel que contendra el panel de datos de los usuarios y el
		 * sistema de límite del servidor
		 */
		Container cLimite = new JPanel();
		cLimite.setLayout(new BoxLayout(cLimite, BoxLayout.X_AXIS));
		cLimite.add(panelDatos);
		cLimite.add(cLimiteUsuarios);

		Container secundaria = new JPanel();
		secundaria.setLayout(new BorderLayout());
		secundaria.add(cBotonera, "North");
		secundaria.add(cListaUsuarios, "West");
		secundaria.add(cLimite, "Center");

		Container principal = new JPanel();
		principal.setLayout(new GridBagLayout());
		principal.add(secundaria);

		this.getContentPane().add(principal);

		// Título, posición, tamaño
		this.setTitle("Ventana del servidor numero: " + v.getId());
		this.setVisible(true);
		this.setBounds(419, 100, 900, 400);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// Terminar del bucle del server a true
				v.setTerminar(true);
				try {
					// Cerramos el serversocket para que no siga esperando
					v.getSs().close();
				} catch (IOException e1) {
				}
				// Cerramos la ventana
				dispose();
			}
		});
		// this.setIconImage(new
		// ImageIcon(getClass().getResource("/icono.jpg")).getImage());
		cargarUsuarios();
	}

	/**
	 * Carga la lista de usuarios conectados al servidor
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void cargarUsuarios() {
		DefaultListModel lm = new DefaultListModel();
		ArrayList<Sesion> lSesiones = v.getlSesiones();
		for (int i = 0; i < lSesiones.size(); i++) {
			lm.addElement(lSesiones.get(i).getActualUser().getLogin());
		}
		this.listaUsuariosON.setModel(lm);
	}

	/**
	 * Actualiza el label de numero de usuarios conectados
	 * 
	 * @param num
	 */
	public void actualizarNumAct(int num) {
		numUsuariosActuales.setText(num + "");
	}

	@SuppressWarnings("rawtypes")
	public void actionPerformed(ActionEvent a) {
		Object o = a.getSource();
		if (o == cbUsuarios) {
			Usuario selected = (Usuario) ((JComboBox) o).getSelectedItem();
			cargarUsuario(selected);
		} else if (o == bDesconectar) {
			String nickL = (String) this.listaUsuariosON.getSelectedValue();
			terminarSesion(nickL);

		} else if (o == bModificarUsuario) {
			GestorBD gestor = GestorBD.getInstance();
			try {
				Usuario selected = (Usuario) ((JComboBox) cbUsuarios)
						.getSelectedItem();
				String loginNuevo = nombreU.getText();
				String pass = contraseña.getText();
				gestor.conectar();
				gestor.modificarUsuario(selected.getLogin(), loginNuevo, pass);
				selected.setLogin(loginNuevo);
				selected.setPassword(pass);
				cargarUsuarios();
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane
						.showMessageDialog(this, "Login ya en uso",
								"Error al modificar usuario",
								JOptionPane.ERROR_MESSAGE);
			}
			gestor.desconectar();
		} else if (o == bCrearUsuario) {
			String loginNuevo = nombreU.getText();
			String pass = contraseña.getText();
			GestorBD gestor = GestorBD.getInstance();
			try {
				gestor.conectar();
				gestor.insertarUsuario(loginNuevo, pass);
				arrayUsuarios.add(new Usuario(loginNuevo, pass));
				actualizarCb();
			} catch (SQLException e) {
				JOptionPane
						.showMessageDialog(this, "Login ya en uso",
								"Error al modificar usuario",
								JOptionPane.ERROR_MESSAGE);
			}
			gestor.desconectar();
		} else if (o == bBorrarUsuario) {
			Usuario selected = (Usuario) ((JComboBox) cbUsuarios)
					.getSelectedItem();
			GestorBD gestor = GestorBD.getInstance();
			gestor.conectar();
			gestor.borrarUsuario(selected.getLogin());
			gestor.desconectar();
			boolean borrado = false;
			for (int i = 0; i < arrayUsuarios.size() && !borrado; i++) {
				if (arrayUsuarios.get(i).getLogin().equals(selected.getLogin())) {
					borrado = true;
					arrayUsuarios.remove(i);
				}
			}
			nombreU.setText("");
			contraseña.setText("");
			terminarSesion(selected.getLogin());
			actualizarCb();
		} else if (o == bCambiarLim) {
			try {
				int nuevoLimite = Integer.parseInt(numUsuariosMax.getText());
				v.setNumMaxUsuarios(nuevoLimite);
			} catch (NumberFormatException e) {
				numUsuariosMax.setText(v.getNumMaxUsuarios() + "");
				JOptionPane.showMessageDialog(this,
						"El valor insertado no es un número",
						"Error al parsear", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Carga los JTextField del login y pass del usuario
	 * 
	 * @param u
	 */
	public void cargarUsuario(Usuario u) {
		this.nombreU.setText(u.getLogin());
		this.contraseña.setText(new String(u.getPassword()));
	}

	/**
	 * Termina la sesion del usuario enviado por parametros
	 * 
	 * @param nickL
	 */
	public void terminarSesion(String nickL) {
		Sesion s = null;
		ArrayList<Sesion> lSesiones = v.getlSesiones();
		boolean encontrado = false;
		for (int i = 0; i < lSesiones.size() && !encontrado; i++) {
			s = lSesiones.get(i);
			if (s.getActualUser().getLogin().equals(nickL)) {
				encontrado = true;
				lSesiones.remove(i);
			}
		}
		if (encontrado) {
			s.terminarSesion();
		}
		cargarUsuarios();
	}

	/**
	 * Actualiza el combobox que contiene todos los usuarios del sistema
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void actualizarCb() {
		cbUsuarios.setModel(new DefaultComboBoxModel(arrayUsuarios.toArray()));
	}

}
