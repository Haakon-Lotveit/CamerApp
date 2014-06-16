package no.uib.CamerApp.tasks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import android.os.AsyncTask;


public class SFTPTask extends AsyncTask<File, Void, Void> {

	@Override
	protected Void doInBackground(File... params) {

		try {
			sendToServer(params[0]);
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException r) {
			r.printStackTrace();
		}
		return null;

	}

	private void sendToServer(File pictureFile) throws JSchException, SftpException, FileNotFoundException {

		String SFTPHOST = "sync.uib.no";
		int SFTPPORT = 22;
		String SFTPUSER = "kni054";
		String SFTPPASS = "8OSTesaus8";
		String SFTPWORKINGDIR = "/Home/stud3/kni054/androidTest/";
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		//		try{ 
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
//		System.out.println(pictureFile.getName());
		channelSftp.put(new FileInputStream(pictureFile), pictureFile.getName());
		//		} catch(Exception ex) {
		//			ex.printStackTrace(); 
		//		} finally {

		session.disconnect();
	}
}
