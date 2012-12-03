package Server;

import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import DB.GestorBD;

public class VentanaAdministrador extends JFrame implements ActionListener, ListSelectionListener{
	private static final long serialVersionUID = 1L;
	private JButton bCrearUsuario;
	private JButton bBorrarUsuario;
	private JButton bModificarUsuario;
	private JButton bDesconectar;
	private JList listaUsuariosON;
	private JComboBox cbUsuarios;
	private JTextField nombreU;
	private JTextField contraseña;
	private JTextField nombreTipoUsuario;
	private Vehiculo v;
	private ArrayList<Usuario>arrayUsuarios;
	
	public VentanaAdministrador(final Vehiculo v){
		this.v=v;
		//Creacion de componentes
		bCrearUsuario=new JButton("Crear");
		bCrearUsuario.addActionListener(this);
		bBorrarUsuario=new JButton("Borrar");
		bBorrarUsuario.addActionListener(this);
		bModificarUsuario=new JButton("Modificar");
		bModificarUsuario.addActionListener(this);
		listaUsuariosON=new JList();
		listaUsuariosON.addListSelectionListener(this);
		nombreU=new JTextField(15);
		contraseña=new JTextField(15);
		nombreTipoUsuario=new JTextField(15);
		nombreTipoUsuario.setEditable(false);
		arrayUsuarios=v.getlUsuarios();
		cbUsuarios=new JComboBox();
		cbUsuarios.setModel(new DefaultComboBoxModel(arrayUsuarios.toArray()));
		cbUsuarios.addActionListener(this);
		bDesconectar=new JButton("Desconectar");
		bDesconectar.addActionListener(this);
		//Creación de la botonera
		Container cBotonera=new JPanel();
		cBotonera.setLayout(new FlowLayout(FlowLayout.LEFT));
		cBotonera.add(bCrearUsuario);
		cBotonera.add(bBorrarUsuario);
		cBotonera.add(bModificarUsuario);
		cBotonera.add(bDesconectar);

		
		//Creacion del panel con la lista de usuarios
		Container cListaUsuarios=new JPanel();
		cListaUsuarios.setLayout(new FlowLayout(FlowLayout.LEFT));
			JScrollPane scListaUsu=new JScrollPane(listaUsuariosON);
			scListaUsu.setPreferredSize(new Dimension(150,200));
		cListaUsuarios.add(scListaUsu);
		
		//Creacion con los datos de usuario
		JLabel nickU=new JLabel("Nickname");
		JLabel passU=new JLabel("Password");
		Container panelDatos=new JPanel();
		panelDatos.setLayout( new GridBagLayout() );
		GridBagConstraints gc = new GridBagConstraints();
	      gc.insets = new Insets(10, 10, 10, 10);
	      gc.gridx = 1;
	      gc.gridy = 1;
		  panelDatos.add(nickU,gc);
		  gc.gridx=2;
		  panelDatos.add(nombreU,gc);
		  gc.gridx=1;
		  gc.gridy=2;
		  panelDatos.add(passU,gc);
		  gc.gridx=2;
		  panelDatos.add(contraseña,gc);


		
		//Creacion panel con el JComboBox con todos los usuarios del sistema
		Container cTipoUsuario=new JPanel();
		cTipoUsuario.setLayout(new FlowLayout(FlowLayout.CENTER));
		cTipoUsuario.add(cbUsuarios);
		
		Container secundaria=new JPanel();
		secundaria.setLayout(new BorderLayout());
		secundaria.add(cBotonera,"North");
		secundaria.add(cListaUsuarios,"West");
		secundaria.add(cTipoUsuario,"East");
		secundaria.add(panelDatos,"Center");
		
		Container principal=new JPanel();
		principal.setLayout(new GridBagLayout());
		principal.add(secundaria);
		
		this.getContentPane().add(principal);
		
		//Título, posición, tamaño	
		this.setTitle("Ventana del servidor numero: "+v.getId());
		this.setVisible(true);
		this.setBounds(419,100,700,400);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				v.setTerminar(true);
				dispose();
			}
		});
		//this.setIconImage(new ImageIcon(getClass().getResource("/icono.jpg")).getImage());
		cargarUsuarios();
	}
	/**
	 * Carga la lista de usuarios conectados al servidor
	 */
	public void cargarUsuarios(){
		DefaultListModel lm=new DefaultListModel();
		ArrayList<Sesion>lSesiones=v.getlSesiones();
		for(int i=0;i<lSesiones.size();i++){
			lm.addElement(lSesiones.get(i).getActualUser().getLogin());
		}			
		this.listaUsuariosON.setModel(lm);
		System.out.println("pasandele por aquile");
	}
	
	public void actionPerformed(ActionEvent a) {
		Object o=a.getSource();
		if(o==cbUsuarios){
			 @SuppressWarnings({ "unused", "rawtypes" })
			Usuario selected =(Usuario) ((JComboBox)o).getSelectedItem();
			 cargarUsuario(selected);
		}else if(o==bDesconectar){
			String nickL=(String)this.listaUsuariosON.getSelectedValue();
			terminarSesion(nickL);
			
		}else if(o==bModificarUsuario){
			GestorBD gestor=GestorBD.getInstance();
			try{
				Usuario selected =(Usuario) ((JComboBox)cbUsuarios).getSelectedItem();
				String loginNuevo=nombreU.getText();
				String pass=contraseña.getText();
				gestor.conectar();
				gestor.modificarUsuario(selected.getLogin(), loginNuevo,pass);
				//gestor.setPassword(selected.getLogin(), pass);
				selected.setLogin(loginNuevo);
				selected.setPassword(pass);
				cargarUsuarios();
			}catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(this,"Login ya en uso", "Error al modificar usuario",JOptionPane.ERROR_MESSAGE);
			}
			gestor.desconectar();
		}else if(o==bCrearUsuario){
			String loginNuevo=nombreU.getText();
			String pass=contraseña.getText();
			GestorBD gestor=GestorBD.getInstance();
			try {
				gestor.conectar();
				gestor.insertarUsuario(loginNuevo, pass);
				arrayUsuarios.add(new Usuario(loginNuevo,pass));
				actualizarCb();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this,"Login ya en uso", "Error al modificar usuario",JOptionPane.ERROR_MESSAGE);
			}
			gestor.desconectar();
		}else if(o==bBorrarUsuario){
			Usuario selected =(Usuario) ((JComboBox)cbUsuarios).getSelectedItem();
			GestorBD gestor=GestorBD.getInstance();
			gestor.conectar();
			gestor.borrarUsuario(selected.getLogin());
			gestor.desconectar();
			boolean borrado=false;
			for(int i=0;i<arrayUsuarios.size()&&!borrado;i++){
				if(arrayUsuarios.get(i).getLogin().equals(selected.getLogin())){
					borrado=true;
					arrayUsuarios.remove(i);
				}
			}
			nombreU.setText("");
			contraseña.setText("");
			terminarSesion(selected.getLogin());
			actualizarCb();
		}
	}
	
	
	
	/**
	 * Pone en el foco en el JTextField seleccionado
	 * @param field
	 */
	private void requestFocus(JTextField field){
		field.requestFocus();
		field.setSelectionStart(0);
		field.setSelectionEnd(field.getText().length());
	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		Object o=event.getSource();
		if(o==listaUsuariosON){
			String nickL=(String)this.listaUsuariosON.getSelectedValue();
			//Para cuando se borra un usuario y note un cambio no salte la excepción de que el usuario no existe
			boolean encontrado=false;
			Usuario s=null;
			try{
				for(int i=0;i<arrayUsuarios.size()&&!encontrado;i++){
					s=arrayUsuarios.get(i);
					if(s.getLogin().equals(nickL)){
						encontrado=true;	
					}
				}
				if(encontrado)
				cargarUsuario(s);
			}catch(Exception e2){
				
			}
		}
		
	}
	/**
	 * Carga los JTextField de la pestaña de gestión de usuarios
	 * @param u
	 */
	public void cargarUsuario(Usuario u){
		this.nombreU.setText(u.getLogin());
		this.contraseña.setText(new String(u.getPassword()));		
	}
	
	public void terminarSesion(String nickL){
		Sesion s=null;
		ArrayList<Sesion>lSesiones=v.getlSesiones();
		boolean encontrado=false;
		for(int i=0;i<lSesiones.size()&&!encontrado;i++){
			s=lSesiones.get(i);
			if(s.getActualUser().getLogin().equals(nickL)){
				encontrado=true;
				lSesiones.remove(i);
			}
		}
		if(encontrado){
		s.terminarSesion();
		try {
			s.getsM().CerrarStreams();
			s.getsM().CerrarSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		cargarUsuarios();
	}
	
	public void actualizarCb(){
		cbUsuarios.setModel(new DefaultComboBoxModel(arrayUsuarios.toArray()));
	}

}
