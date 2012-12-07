package Client;

import java.util.ArrayList;

import javax.swing.JFrame;

import Server.Sensor;

public class VentanaListSensor extends JFrame{

	private static final long serialVersionUID = 1383908840020968979L;
	private javax.swing.JLabel lOK;
	private javax.swing.JLabel lFinal;
	private javax.swing.JScrollPane sTabla;
	private javax.swing.JTable tSensores;
	private ArrayList<String> lSensores;
	
	public VentanaListSensor(ArrayList<String> lSensores)	{
		
		//Inicializar componentes
		lOK = new javax.swing.JLabel();
        sTabla = new javax.swing.JScrollPane();
        tSensores = new javax.swing.JTable();
        lFinal = new javax.swing.JLabel();
        this.lSensores=lSensores;

        lOK.setText("112 OK Lista de sensores");
        lFinal.setText("212 OK Lista finalizada");

        tSensores.setModel(new javax.swing.table.DefaultTableModel(
            listaSensores(lSensores),
            new String [] {
                "ID Sensor", "Descripción", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        sTabla.setViewportView(tSensores);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(sTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(lOK))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(195, 195, 195)
                        .addComponent(lFinal)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lOK)
                .addGap(18, 18, 18)
                .addComponent(sTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(lFinal)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        
        pack();
	}
	
public String[][] listaSensores(ArrayList<String> l)	{
	l.remove(l.size()-1); //Elimina el último
	l.remove(0); //Elimina el primero
	
	String[][] sensores = new String[l.size()][3];
	for(int j=0;j<l.size();j++)	{
		String cadena=l.get(j);	
		String[] sCadena = cadena.split(";");
		System.out.println(sCadena.length);
		for(int i=0;i<3;i++)	{			
			switch(i)	{
			case 0: //ID		
				sensores[j][i]=sCadena[i]; break;
			case 1:	//Descripcion
				sensores[j][i]=sCadena[i]; break;
			case 2: //Estado
				if(sCadena[i].equals("true"))	{	//ON
					sensores[j][i]="ON";
				}	else	{
					sensores[j][i]="OFF";
				}
				break;
			}
		}
	}
	
	return sensores;
}
	
}
