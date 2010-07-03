// CatfoOD 2009-9-18 ����03:25:48

package jym.command;

import jym.jdbc.SqlLinked;

public interface IContainer {
	/**
	 * ��ӡָ���������ϸ����
	 * @param command
	 */
	void help(String command);
	/**
	 * �˳�ϵͳ
	 */
	void exit();
	/**
	 * �г����п�������
	 */
	void listCommand();
	/**
	 * ִ��sql���
	 * @return �ɹ�����true
	 */
	boolean doSql(String sql);
	/**
	 * ִ��һ������ 
	 * @return �ɹ�����true
	 */
	boolean doCommand(String commands);
	/**
	 * ִ��һ������ 
	 * @param args - ����0Ϊ������
	 * @return �ɹ�����true
	 */
	boolean doCommand(String[] args);
	/**
	 * �л���ָ����������
	 * @param linkname - ָ����������linkname������Login.getLinkedNames()�е�Ԫ��
	 * @return
	 */
	boolean switchLink(String linkname);
	/**
	 * ȡ�õ�ǰ��¼����Ϣ
	 * @return ���ص�Loing�����У������п���������
	 */
	Login getLogin();
	/**
	 * ȡ������ʹ�õ�����
	 * @return ���ص�SqlLinked�������ִ��sql��䣬����ȡ��������Ϣ
	 */
	SqlLinked getCurrentLink();
	/**
	 * ȡ�õĴ�ӡ�����ṩ������û�����Ĺ���<br>
	 * <b>��Ҫֱ��ʹ��System.out.println()�����</b>
	 */
	IPrinter getPrinter();
}
