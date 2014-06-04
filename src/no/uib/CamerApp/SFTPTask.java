package no.uib.CamerApp;

import java.io.File;
import java.io.FileInputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import android.os.AsyncTask;


public class SFTPTask extends AsyncTask<File, Void, Void> {

	@Override
	protected Void doInBackground(File... params) {

		try {
			sendToServer(params[0]);
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	private void sendToServer(File pictureFile) throws JSchException {

		String SFTPHOST = "sync.uib.no";
		int SFTPPORT = 22;
		String SFTPUSER = "username";
		String SFTPPASS = "p4ssw0rd";
		String SFTPWORKINGDIR = "/Home/stud3/kni054/androidTest/";
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		try{ 
			JSch jsch = new JSch();
			session = jsch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
			session.setPassword(SFTPPASS);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config); 
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect(); 
			channelSftp = (ChannelSftp)channel;
			channelSftp.cd(SFTPWORKINGDIR);
			//			  File f = new File(FILETOTRANSFER);
			System.out.println(pictureFile.getName());
			channelSftp.put(new FileInputStream(pictureFile), pictureFile.getName());
		} catch(Exception ex) {
			ex.printStackTrace(); 
		} finally {
			
			session.disconnect();
		}
	}

}
