package Client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class VentanaFoto extends JFrame implements ActionListener {

	private static final long serialVersionUID = -4167193421482312976L;
	private javax.swing.JLabel lMensaje;
	private javax.swing.JLabel lImagen;
	private javax.swing.JButton bGetLoc;
	private javax.swing.JPanel pFoto;
	private Cliente c;
	private VentanaMenu vMenu;
	private boolean coordSolicitadas;
	private VentanaFoto vFoto;

	public VentanaFoto(final Cliente c, final VentanaMenu vMenu, ImageIcon i) {
		vFoto = this;
		this.c = c;
		this.vMenu = vMenu;
		this.vMenu.setEnabled(false);
		lMensaje = new javax.swing.JLabel();
		bGetLoc = new javax.swing.JButton();
		pFoto = new javax.swing.JPanel();
		lImagen = new javax.swing.JLabel(i);
		lMensaje.setText("					");
		bGetLoc.setText("Obtener Localizacion");
		coordSolicitadas = false;

		lMensaje.setText("206 OK Foto geolocalizada recibida.");
		lMensaje.setForeground(new Color(50, 205, 50));

		javax.swing.GroupLayout pFotoLayout = new javax.swing.GroupLayout(pFoto);
		pFoto.setLayout(pFotoLayout);
		pFotoLayout.setHorizontalGroup(pFotoLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				lImagen, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		pFotoLayout.setVerticalGroup(pFotoLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				lImagen, javax.swing.GroupLayout.DEFAULT_SIZE, 239,
				Short.MAX_VALUE));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(48, 48, 48)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING,
												false)
												.addComponent(
														pFoto,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		lMensaje)
																.addGap(135,
																		135,
																		135)
																.addComponent(
																		bGetLoc)))
								.addContainerGap(47, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(26, 26, 26)
								.addComponent(pFoto,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(18, 18,
																		18)
																.addComponent(
																		lMensaje)
																.addContainerGap(
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE))
												.addGroup(
														layout.createSequentialGroup()
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		35,
																		Short.MAX_VALUE)
																.addComponent(
																		bGetLoc)
																.addGap(24, 24,
																		24)))));

		bGetLoc.addActionListener(this);

		// Para cuando le da a la x se comporte como si fuese el boton cancelar
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (!coordSolicitadas) {
					JOptionPane.showMessageDialog(vFoto,
							"Tienes que obtener las coordenadas",
							"Error al salir", JOptionPane.ERROR_MESSAGE);
					if (c != null) {
						try {
							c.SALIR();
						} catch (IOException e1) {
							System.out.println("Error al Salir");
							e1.printStackTrace();
						}
					}
					vMenu.dispose();
					dispose();
				}
			}
		});
		pack();
	}

	public static void main(String[] args) {
		/*
		 * VentanaFoto v = new VentanaFoto(); v.setVisible(true);
		 */
	}

	public void actionPerformed(ActionEvent arg0) {
		Object o = arg0.getSource();
		if (o == bGetLoc) {
			try {
				bGetLoc.setEnabled(false);
				vMenu.setEnabled(true);
				coordSolicitadas = true;
				String mensaje = c.getLoc();
				lMensaje.setText(mensaje);
				if (mensaje.contains("114")) {
					lMensaje.setForeground(new Color(50, 205, 50));
				} else if (mensaje.contains("417") || mensaje.contains("418")) {
					lMensaje.setForeground(new Color(255, 0, 0));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
