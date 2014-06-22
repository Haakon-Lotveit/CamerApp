package no.uib.CamerApp.tasks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.os.AsyncTask;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;


public class SFTPTask extends AsyncTask<File, Void, Void> {



	private String user;

	public SFTPTask(String user) {
		this.user = user;
	}

	@Override
	protected Void doInBackground(File... params) {

		try {
			File file = (File) params[0];
			if(file.length() > 0) {
				sendToServer(params[0]);
			}

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
		} finally {
			File file = (File) params[0];
			file.delete();
		}
		return null;

	}

	private void sendToServer(File pictureFile) throws JSchException, SftpException, FileNotFoundException {

		String SFTPHOST = "185.35.184.206";
		int SFTPPORT = 22;
		String SFTPUSER = "camerapp";
		String SFTPPASS = "camerApp-2014";
		String SFTPWORKINGDIR = "/home/camerapp/camerapp/";
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
		
		// Create folder
		ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
		channelExec.setCommand("mkdir -p " + SFTPWORKINGDIR + user);
        channelExec.connect();
		
		// SFTP
		channel = session.openChannel("sftp");
		channel.connect(); 
		channelSftp = (ChannelSftp)channel;
		channelSftp.cd(SFTPWORKINGDIR + user);
		//			  File f = new File(FILETOTRANSFER);
		//		System.out.println(pictureFile.getName());
		
		channelSftp.put(new FileInputStream(pictureFile), pictureFile.getName());
		//		} catch(Exception ex) {
		//			ex.printStackTrace(); 
		//		} finally {
		session.disconnect();
		//		pictureFile.delete();
	}
}
