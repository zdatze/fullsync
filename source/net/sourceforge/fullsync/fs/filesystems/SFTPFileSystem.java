/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA 02110-1301, USA.
 *
 * For information about the authors of this project Have a look
 * at the AUTHORS file in the root of this project.
 */
package net.sourceforge.fullsync.fs.filesystems;

import net.sourceforge.fullsync.ConnectionDescription;
import net.sourceforge.fullsync.FileSystemException;
import net.sourceforge.fullsync.fs.Site;
import net.sourceforge.fullsync.fs.connection.CommonsVfsConnection;
import net.sourceforge.fullsync.impl.SFTPLogger;

import org.apache.log4j.Logger;

import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

public class SFTPFileSystem extends CommonsVfsFileSystem implements UIKeyboardInteractive, UserInfo {
	private static boolean loggerSetupCompleted = false;

	private Logger logger = Logger.getLogger("FullSync");
	private ConnectionDescription desc = null;

	public SFTPFileSystem() {
		if (!loggerSetupCompleted) {
			com.jcraft.jsch.JSch.setLogger(new SFTPLogger());
		}
	}

	@Override
	public final Site createConnection(final ConnectionDescription description) throws FileSystemException {
		this.desc = description;
		return new CommonsVfsConnection(description, this);
	}

	@Override
	public final String getPassphrase() {
		logger.debug("SFTP UserInfo::getPassphrase");
		return desc.getSecretParameter("keyPassphrase");
	}

	@Override
	public final String getPassword() {
		logger.debug("SFTP UserInfo::getPassword");
		return desc.getSecretParameter("password");
	}

	@Override
	public final boolean promptPassword(final String message) {
		logger.debug("SFTP UserInfo::promptPassword: " + message);
		return true;
	}

	@Override
	public final boolean promptPassphrase(final String message) {
		logger.debug("SFTP UserInfo::promptPassphrase: " + message);
		return true;
	}

	@Override
	public final boolean promptYesNo(final String message) {
		logger.warn("SFTP UserInfo::promptYesNo: " + message + "; automatic decision: No");
		return false;
	}

	@Override
	public final void showMessage(final String message) {
		logger.warn("SFTP UserInfo::showMessage: " + message);
	}

	@Override
	public final String[] promptKeyboardInteractive(final String destination, final String name, final String instruction, final String[] prompt, final boolean[] echo) {
		logger.warn("Suppressed promptKeyboardInteractive:");
		logger.warn("Destination: " + destination);
		logger.warn("Name: " + name);
		logger.warn("Instruction: " + instruction);
		logger.warn("Prompt (#" + prompt.length + "): " + prompt);
		logger.warn("echo: (#" + echo.length + "): " + echo);
		logger.warn("rejecting prompt automatically");
		return null;
	}
}
