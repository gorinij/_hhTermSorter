package app;

import static app.Main.map;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author gorjnich
 */
public class V_swing {

    public V_swing() {
        /* ���� ����� ������ */
        JTextField vacancyField = new JTextField();
        vacancyField.setSize(new Dimension(100, 20));
        map.put("textField", vacancyField);

        /* ���� ���������� �������������� �������� */
        SpinnerModel snipperModel = new SpinnerNumberModel(10, 1, 100, 1);
        JSpinner vacancyCounter = new JSpinner(snipperModel);
        map.put("vacancyCounter", vacancyCounter);

        /* ������ ������ ������ */
        JButton searchButton = new JButton("�����");

        /* ������, ��� ����������� ���������� ������ �������� */
        DefaultListModel termListModel = new DefaultListModel();
        JList<String> termList = new JList<>(termListModel);
        termList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        map.put("list", termListModel);

        /* ��������� ��� ������ �������� */
        JScrollPane termListScrollPane = new JScrollPane(termList);

        /* ������ ��� ��������� */
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
        topPanel.add(vacancyField);
        topPanel.add(vacancyCounter);
        topPanel.add(searchButton);
        JPanel generalPanel = new JPanel();
        generalPanel.setLayout(new BorderLayout());
        generalPanel.add(topPanel, BorderLayout.NORTH);
        generalPanel.add(termListScrollPane, BorderLayout.CENTER);

        /* ���������� ������� */
        /* ������ ������� "�����" */
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JSpinner vacancyCounter = (JSpinner) map.get("vacancyCounter");
                int countOfVacancies = (int) vacancyCounter.getValue();
                DefaultListModel list = (DefaultListModel) map.get("list");
                list.clear();
                JTextField textField = (JTextField) map.get("textField");
                String searchedVacancy = textField.getText();
                Engine engine = new Engine(countOfVacancies, searchedVacancy);
                ArrayList<String> terms = engine.getSortedTerms();
                for (String t : terms) {
                    list.addElement(t);
                }
            }
        });
        /* ������ ������� "Enter" �� ��������� ������ */
        vacancyField.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    JSpinner vacancyCounter = (JSpinner) map.get("vacancyCounter");
                    int countOfVacancies = (int) vacancyCounter.getValue();
                    DefaultListModel list = (DefaultListModel) map.get("list");
                    list.clear();
                    JTextField textField = (JTextField) map.get("textField");
                    String searchedVacancy = textField.getText();
                    Engine engine = new Engine(countOfVacancies, searchedVacancy);
                    ArrayList<String> terms = engine.getSortedTerms();
                    for (String t : terms) {
                        list.addElement(t);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        /* �������� ������ �������� ���� */
        JFrame mainWindowFrame = new JFrame();
        mainWindowFrame.add(generalPanel);
        mainWindowFrame.setSize(new Dimension(500, 500));
        mainWindowFrame.setVisible(true);
        mainWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
