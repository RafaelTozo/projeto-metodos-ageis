import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIRecuperacao {

    public GUIRecuperacao() {
        JFrame frame = new JFrame("Recuperacao");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        placeComponents(panel);

        frame.setVisible(true);

    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Email:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 160, 25);
        panel.add(userText);

        JButton sendButton = new JButton("Enviar email");
        sendButton.setBounds(10, 80, 150, 25);
        panel.add(sendButton);

        JButton returnButton = new JButton("Voltar");
        returnButton.setBounds(200, 80, 150, 25);
        panel.add(returnButton);

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


            }
        });

        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new GUILogin();
            }
        });
    }

    private void telaCodiogo(){

    }

    private void telaNovaSenha(){

    }
}
