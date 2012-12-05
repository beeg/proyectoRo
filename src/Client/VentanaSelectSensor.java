package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import Server.Medida;
import Server.Sensor;

public class VentanaSelectSensor extends JFrame implements ActionListener {

	private static final long serialVersionUID = -8986779483757784983L;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JLabel lSensor;
	private javax.swing.JComboBox<String> cbSensor;
	private javax.swing.JButton bAceptar;
	private javax.swing.JButton bCancelar;
	private javax.swing.JLabel lMensaje;
	private ArrayList<String> lSensores;

	public VentanaSelectSensor(ArrayList<String> lSensores) {
		jPanel1 = new javax.swing.JPanel();
		lSensor = new javax.swing.JLabel();
		cbSensor = new javax.swing.JComboBox<String>();
		bAceptar = new javax.swing.JButton();
		bCancelar = new javax.swing.JButton();
		lMensaje = new javax.swing.JLabel();
		this.lSensores = lSensores;
		cbSensor.setModel(new javax.swing.DefaultComboBoxModel<String>(
				(listaSensores(lSensores))));

		lSensor.setText("Selecciona el sensor:");
		bAceptar.setText("Aceptar");
		bCancelar.setText("Cancelar");
		lMensaje.setText("");

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel1Layout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(lSensor)
						.addGap(18, 18, 18)
						.addComponent(cbSensor,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(89, Short.MAX_VALUE)));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGap(46, 46, 46)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(lSensor)
														.addComponent(
																cbSensor,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(41, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(79, 79,
																		79)
																.addComponent(
																		jPanel1,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(103,
																		103,
																		103)
																.addComponent(
																		bAceptar)
																.addGap(18, 18,
																		18)
																.addComponent(
																		bCancelar)))
								.addGap(0, 7, Short.MAX_VALUE))
				.addComponent(lMensaje,
						javax.swing.GroupLayout.Alignment.TRAILING,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jPanel1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(bAceptar)
												.addComponent(bCancelar))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										28, Short.MAX_VALUE)
								.addComponent(lMensaje,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										17,
										javax.swing.GroupLayout.PREFERRED_SIZE)));

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		// Listeners
		cbSensor.addActionListener(this);
		bAceptar.addActionListener(this);
		bCancelar.addActionListener(this);

		pack();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public String[] listaSensores(ArrayList<String> l) {
		String[] sensores = new String[l.size()];
		for (int i = 0; i < l.size(); i++) {
			sensores[i] = l.get(i);
		}
		return sensores;
	}

	/*
	 * public static void main(String[] args) { ArrayList<Sensor> listaS = new
	 * ArrayList<Sensor>(); listaS.add(new Sensor(4,"prueba",true, new
	 * ArrayList<Medida>())); VentanaSelectSensor v = new
	 * VentanaSelectSensor(listaS); v.setVisible(true); }
	 */

}
