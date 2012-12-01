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
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
	
	public VentanaAdministrador(Vehiculo v){
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
		cbUsuarios=new JComboBox(v.getlUsuarios().toArray());
		cbUsuarios.addActionListener(this);
		arrayUsuarios=v.getlUsuarios();
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
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//this.setIconImage(new ImageIcon(getClass().getResource("/icono.jpg")).getImage());
		cargarUsuarios();
	}
	
	/**
	 * Metodo para ir insertamdo lineas al box layout
	 * @param panelContenidos donde se insertar el nuevo panel
	 * @param l JLabel que se inserta
	 * @param c Componente que se inserta
	 */
	public static void añadirFila(Container panelContenidos,JLabel l, Component c){
		JPanel pTemp = new JPanel();
		pTemp.setLayout( new FlowLayout(FlowLayout.LEFT) );
		pTemp.add(l);
		pTemp.add(c);
		panelContenidos.add(pTemp);
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
			 boolean encontrado=false;
			 Usuario seleccionado=null;
			 for(int i=0;i<arrayUsuarios.size()&&!encontrado;i++){
				 seleccionado=arrayUsuarios.get(i);
				 if(seleccionado.getLogin().equals(selected)){
					 encontrado=false;
				 }
			 }
			 cargarUsuario(selected);
		}else if(o==bDesconectar){
			String nickL=(String)this.listaUsuariosON.getSelectedValue();
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
			s.terminarSesion();
			try {
				s.getsM().CerrarStreams();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cargarUsuarios();
			
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

}
