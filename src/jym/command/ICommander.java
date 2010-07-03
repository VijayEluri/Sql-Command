// CatfoOD 2009-9-18 ����11:33:53 
package jym.command;

/**
 * ÿ���������ʵ�ֵķ�����ͬʱ�ṩһ���޲ι���
 */
public interface ICommander {
	/**
	 * �������<b>��������׳�Exception������</b>
	 * @param d - ����ִ�еĲ���
	 * @param args - ����Ĳ��� args[0] Ϊ���������
	 * @return - �ɹ�����true,ʧ�ܷ���false,��ʱ���������ӡ����
	 */
	boolean doit(IContainer d, String[] args);
	
	/**
	 * ȡ�����������
	 */
	String getCommandName();
	
	/**
	 * ȡ�ð����ַ���
	 */
	String getHelp();
	
	/**
	 * ȡ�ü�̵�˵��
	 */
	String getInfo();
}
