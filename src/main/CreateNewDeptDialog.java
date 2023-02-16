package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.Departamento;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.Window;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateNewDeptDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldUbicacion;
	private JTextField textFieldNombreDept;
	private JButton okButton;
	
	private Departamento departamentoACrear=null;
	
	public Departamento getResult() {
		return this.departamentoACrear;
	}

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			CreateNewDeptDialog dialog = new CreateNewDeptDialog();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public void initComponents() {
		setTitle("Nuevo departamento");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblDeptName = new JLabel("Nombre departamento");
		lblDeptName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDeptName.setBounds(39, 34, 140, 24);
		contentPanel.add(lblDeptName);

		textFieldUbicacion = new JTextField();
		textFieldUbicacion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldUbicacion.setBounds(227, 82, 197, 23);
		contentPanel.add(textFieldUbicacion);
		textFieldUbicacion.setColumns(10);

		JLabel lblDeptLocation = new JLabel("Ubicación");
		lblDeptLocation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDeptLocation.setBounds(39, 82, 140, 24);
		contentPanel.add(lblDeptLocation);

		textFieldNombreDept = new JTextField();
		textFieldNombreDept.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldNombreDept.setColumns(10);
		textFieldNombreDept.setBounds(227, 40, 197, 23);
		contentPanel.add(textFieldNombreDept);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		okButton = new JButton("Crear");

		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				departamentoACrear=null;
				CreateNewDeptDialog.this.dispose();
				
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		ActionListener crearBtnActionListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!(textFieldUbicacion.getText().trim().equals(""))
						&& !(textFieldNombreDept.getText().trim().equals(""))) {
					departamentoACrear = new Departamento();
					departamentoACrear.setDname(textFieldNombreDept.getText().trim());
					departamentoACrear.setLoc(textFieldUbicacion.getText().trim());
					CreateNewDeptDialog.this.dispose();
				}
			}
		};

		this.okButton.addActionListener(crearBtnActionListener);

	}

	public CreateNewDeptDialog(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initComponents();
		this.setLocationRelativeTo(owner);
	}
	
	
}
