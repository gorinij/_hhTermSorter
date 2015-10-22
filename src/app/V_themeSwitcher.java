package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

/**
 *
 * @author gorjnich
 */
public class V_themeSwitcher {

    /**
     * Головная функция
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGUI();
            }
        });
    }

    private static void createGUI() {
        /* Создаем объект списка */
        JList<String> list = new JList<>();
        /* Устанавливаем способ выбора элементов списка как одиночный */
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        /* Компонент для обеспечения скроллинга и добавляем туда наш список */
        JScrollPane listScrollPane = new JScrollPane(list);

        /* Создаем вверхнюю панель */
        JPanel topPanel = new JPanel();
        /* Создаем Layout вида BorderLayout, делящий окно на пять частей света */
        topPanel.setLayout(new BorderLayout());
        /* Добавляем на верхнюю панель прокручиваемый список */
        topPanel.add(listScrollPane, BorderLayout.CENTER);

        /* Интерфейс для прослушивания событий. Обрабатывается кнопка обновления */
        ActionListener updateButtonListener = new UpdateListAction(list);
        /* ? */
        updateButtonListener.actionPerformed(
                new ActionEvent(list, ActionEvent.ACTION_PERFORMED, null)
        );

        /* Создаем кнопку "Обновить список" */
        JButton updateListButton = new JButton("Update list");
        /* Создаем кнопку "Сменить тему" */
        JButton updateLookAndFeelButton = new JButton("Update Look&Feel");

        /* Создаем панель для кнопок */
        JPanel btnPanel = new JPanel();
        /* 
         Устанавливаем лайоут панели в качесте BoxLayout. Элементы будут 
         распологаться в линию
         */
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));
        /* Добавляем на панель кнопку "Обновить список" */
        btnPanel.add(updateListButton);
        /* Создается элемент устанавливаем расстояние между кнопками в 5 пикселей */
        btnPanel.add(Box.createHorizontalStrut(5));
        /* Добавляем на панель кнопку "Сменить тему" */
        btnPanel.add(updateLookAndFeelButton);

        /* Создаем нижнюю панель */
        JPanel bottomPanel = new JPanel();
        /* Добавляем на панель кнопочную панель */
        bottomPanel.add(btnPanel);

        /* Создаем общую панель */
        JPanel panel = new JPanel();
        /* Устанавливаем границы в 5 пикселей вокруг*/
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        /* Делим панель в 5 частей мира */
        panel.setLayout(new BorderLayout());
        /* Добавляем туда вверхнюю панель и располагаем по центру */
        panel.add(topPanel, BorderLayout.CENTER);
        /* Добавляем туда нижнюю панель */
        panel.add(bottomPanel, BorderLayout.SOUTH);

        /* Создаем оконный фрейм */
        JFrame frame = new JFrame("Look%Feel Swticher");
        /* Устанавливаем минимальное возможный для экрана размер в 300x200 */
        frame.setMinimumSize(new Dimension(300, 200));
        /* Назначаем кнопке "Крестик" функцию закрытия */
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        /* Добавляем общую панель на оконный фрейм*/
        frame.add(panel);
        /* 
         Устанавливает минимальный размер окна достаточный для отображения 
         всех компонентов
         */
        frame.pack();
        /* Делаем окно видимым */
        frame.setVisible(true);

        /* Устанавливаем листенер для кнопки "Сменить тему" */
        updateListButton.addActionListener(updateButtonListener);
        updateLookAndFeelButton.addActionListener(
                new UpdateLookAndFeelAction(frame, list)
        );
    }

    static class UpdateListAction implements ActionListener {

        private JList<String> list;

        private UpdateListAction(JList<String> list) {
            this.list = list;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            ArrayList<String> lookAndFeelList = new ArrayList<>();
            UIManager.LookAndFeelInfo[] infoArray
                    = UIManager.getInstalledLookAndFeels();
            int lookAndFeelIndex = 0;
            int currentLookAndFeelIndex = 0;
            String currentLookAndFeelClassName
                    = UIManager.getLookAndFeel().getClass().getName();

            for (UIManager.LookAndFeelInfo info : infoArray) {
                if (info.getClassName().equals(currentLookAndFeelClassName)) {
                    currentLookAndFeelIndex = lookAndFeelIndex;
                }
                lookAndFeelList.add(info.getName());
                lookAndFeelIndex++;
            }

            String[] listDataArray = new String[lookAndFeelList.size()];
            final String[] newListData
                    = lookAndFeelList.toArray(listDataArray);
            final int newSelectedIndex = currentLookAndFeelIndex;

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    list.setListData(newListData);
                    list.setSelectedIndex(newSelectedIndex);
                }
            });
        }
    }

    static class UpdateLookAndFeelAction implements ActionListener {

        private JList<String> list;
        private JFrame rootFrame;

        public UpdateLookAndFeelAction(JFrame frame, JList<String> list) {
            this.rootFrame = frame;
            this.list = list;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String lookAndFeelName = list.getSelectedValue();
            UIManager.LookAndFeelInfo[] infoArray
                    = UIManager.getInstalledLookAndFeels();

            for (UIManager.LookAndFeelInfo info : infoArray) {
                if (info.getName().equals(lookAndFeelName)) {
                    String message = "Look&feel was changed to " + lookAndFeelName;
                    try {
                        UIManager.setLookAndFeel(info.getClassName());
                        SwingUtilities.updateComponentTreeUI(rootFrame);
                    } catch (ClassNotFoundException e1) {
                        message = "Error: " + info.getClassName() + " not found";
                    } catch (InstantiationException e1) {
                        message = "Error: instantiation exception";
                    } catch (IllegalAccessException e1) {
                        message = "Error: illegal access";
                    } catch (UnsupportedLookAndFeelException e1) {
                        message = "Error: unsupported look and feel";
                    }
                    JOptionPane.showMessageDialog(null, message);
                    break;
                }
            }
        }
    }

}
