package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class VentanaFoto extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = -4167193421482312976L;
	private javax.swing.JLabel lMensaje;
	private javax.swing.JLabel lImagen;
	private javax.swing.JButton bGetLoc;
	private javax.swing.JPanel pFoto;
	
	public VentanaFoto(ImageIcon i)	{
	     lMensaje = new javax.swing.JLabel();
	     bGetLoc = new javax.swing.JButton();
	     pFoto = new javax.swing.JPanel();
	     lImagen = new javax.swing.JLabel(i);

	     lMensaje.setText("jLabel1");
	     bGetLoc.setText("Obtener Localizacion");

	        javax.swing.GroupLayout pFotoLayout = new javax.swing.GroupLayout(pFoto);
	        pFoto.setLayout(pFotoLayout);
	        pFotoLayout.setHorizontalGroup(
	            pFotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addComponent(lImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	        );
	        pFotoLayout.setVerticalGroup(
	            pFotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addComponent(lImagen, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
	        );

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addGap(48, 48, 48)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                    .addComponent(pFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addGroup(layout.createSequentialGroup()
	                        .addComponent(lMensaje)
	                        .addGap(135, 135, 135)
	                        .addComponent(bGetLoc)))
	                .addContainerGap(52, Short.MAX_VALUE))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addGap(26, 26, 26)
	                .addComponent(pFoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(layout.createSequentialGroup()
	                        .addGap(18, 18, 18)
	                        .addComponent(lMensaje)
	                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	                    .addGroup(layout.createSequentialGroup()
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
	                        .addComponent(bGetLoc)
	                        .addGap(24, 24, 24))))
	        );

	        pack();
	}
	
	public static void main(String[] args)	{
		/*VentanaFoto v = new VentanaFoto();
		v.setVisible(true);*/
	}

	public void actionPerformed(ActionEvent arg0) {
		Object o = arg0.getSource();
		if(o==bGetLoc){
			
		}		
	}
}
