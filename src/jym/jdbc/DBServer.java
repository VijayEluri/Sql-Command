// CatfoOD 2009-9-16 ����03:32:55 
package jym.jdbc;

import jym.command.dbtype.IDBCommand;


public class DBServer {
	private String url;
	private String cn;
	private IDBCommand cmd;
	
	public DBServer(String urL, String classname) {
		url = urL;
		cn = classname;
		cmd = null;
	}
	
	/**
	 * ��������������װ���ݿ�jdbc url
	 * @param conf - ��������
	 * @return װ��õ�url������������Ч��
	 */
	public String getUrl(ConnectConfig conf) {
		return getUrl(
				conf.getServerAdd(),
				conf.getPort(),
				conf.getServerName()
				);
	}
	
	public String getUrl(String serverAdd, String port, String name) {
		return getUrl( new Object[]{
				serverAdd,
				port,
				name,
		});
	}
	
	public void registerDriver() throws ClassNotFoundException {
		Class.forName(cn);
	}
	
	/**
	 * ֻ��ǰ����������Ч
	 */
	private String getUrl(Object ... args) {
		return String.format(url, args);
	}
	
	public String getClassName() {
		return cn;
	}
	
	/**
	 * ȡ�������ݿ�󶨵����
	 */
	public IDBCommand getDBCommand() {
		return cmd;
	}
	
	/**
	 * ��һ����������ݿ�
	 * @return - ���û�аﶨ�κ��������null
	 */
	public void setDBCommand(IDBCommand ic) {
		cmd = ic;
	}
}
