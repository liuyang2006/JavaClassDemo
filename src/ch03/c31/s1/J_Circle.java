package ch03.c31.s1;
// 文件名: J_Circle.java; 开发者: 雍俊海
import java.awt.Color;
import java.awt.Graphics;

public class J_Circle {
	private int m_centerX, m_centerY; // 圆心
	private int m_radius; // 半径，要求: m_radius>=1
	private Color m_color;
	private boolean m_isSolid; // true: 实心圆; false :空心圆

	J_Circle() {
		mb_set(0, 0, 1, Color.black, false);
	} // J_Circle构造方法结束

	J_Circle(int x, int y, int r) {
		mb_set(x, y, r, Color.black, false);
	} // J_Circle构造方法结束

	J_Circle(int x, int y, int r, Color c, boolean s) {
		mb_set(x, y, r, c, s);
	} // J_Circle构造方法结束

	public void mb_set(int x, int y, int r, Color c, boolean s) {
		mb_setCenter(x, y);
		mb_setRadius(r);
		mb_setColor(c);
		mb_setSolid(s);
	} // 方法mb_set结束

	public void mb_setCenter(int x, int y) {
		m_centerX = x;
		m_centerY = y;
	} // 方法mb_setCenter结束

	public void mb_setColor(Color c) {
		m_color = c;
	} // 方法mb_setColor结束

	public void mb_setRadius(int r) {
		m_radius = (r < 0 ? 1 : r);
	} // 方法mb_setRadius结束

	public void mb_setSolid(boolean s) {
		m_isSolid = s;
	} // 方法mb_setSolid结束

	public void mb_draw(Graphics g) {
		int diameter = m_radius + m_radius;
		Color c = g.getColor();
		g.setColor(m_color);
		if (m_isSolid)
			g.fillOval(m_centerX - m_radius, m_centerY - m_radius, diameter, diameter);
		else
			g.drawOval(m_centerX - m_radius, m_centerY - m_radius, diameter, diameter);
		g.setColor(c);
	} // 方法mb_draw结束

} // 类J_Circle结束
