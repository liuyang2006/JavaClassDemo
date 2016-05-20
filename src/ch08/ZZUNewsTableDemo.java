package ch08;

import ch05.HtmlStringMatcher;
import ch05.News;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZZUNewsTableDemo extends JFrame {
    public ZZUNewsTableDemo() throws IOException {
        super("郑州大学主页新闻GUI FirstGUIDemo");

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());

        JButton btnLoadData = new JButton("加载新闻");
        JButton btnClearData = new JButton("清除新闻");

        jPanel.add(btnLoadData);
        jPanel.add(btnClearData);
        add(jPanel, BorderLayout.NORTH);

        JTable table = new JTable();
        NewsTableModel newsTableModel = new NewsTableModel();

        table.setModel(newsTableModel);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (table.getSelectedRow() > -1) {
                    // print first column value from selected row
                    String href = table.getValueAt(table.getSelectedRow(), 2).toString();
                    System.out.println("open news href = " + href);
                    try {
                        Runtime.getRuntime().exec("open " + href);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));

        //表格常常放在滚动面板中。
        JScrollPane scrollPane = new JScrollPane(table);

        //将滚动面板放在窗口中。
        add(scrollPane, BorderLayout.CENTER);

        btnLoadData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<News> newsList = null;
                try {
                    newsList = HtmlStringMatcher.getNewsListFromZZU();
                    newsTableModel.setData(newsList);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                table.revalidate();
                System.out.println("Data loaded from net. Number of news = " + newsList.size());
            }
        });


        btnClearData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newsTableModel.clearData();
                table.revalidate();
                System.out.println("Clear data ok.");
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        pack();
    }

    /*自定义表格数据模型。其中的方法都是AbstractTableModel中的方法。
      该类是内部类。*/
    class NewsTableModel extends AbstractTableModel {
        List<News> newsList;

        public NewsTableModel() {
            newsList = new ArrayList<>();
        }

        public void setData(List<News> theNewsList) {
            newsList.clear();
            newsList.addAll(theNewsList);
        }

        public void clearData() {
            newsList.clear();
        }

        //定义表格的列名。
        final String[] columnNames = {"日期", "新闻标题", "网址链接"};

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return newsList == null ? 0 : newsList.size();
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            if (newsList == null)
                return null;
            News news = newsList.get(row);
            switch (col) {
                case 0:
                    return news.getDate();
                case 1:
                    return news.getTitle();
                case 2:
                    return news.getHref();
                default:
                    throw new IllegalArgumentException("no col " + col);
            }
        }


    }


    public static void main(String[] args) throws IOException {
        ZZUNewsTableDemo frame = new ZZUNewsTableDemo();
        frame.pack();
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setLocation(200, 200);
    }
}


