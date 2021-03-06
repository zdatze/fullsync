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
package net.sourceforge.fullsync.fs.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;

import net.sourceforge.fullsync.ConnectionDescription;
import net.sourceforge.fullsync.fs.File;
import net.sourceforge.fullsync.fs.FileSystem;

import org.apache.commons.vfs2.Capability;
import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.VFS;

public class CommonsVfsConnection implements FileSystemConnection {
	private static final long serialVersionUID = 2L;
	private final boolean canSetLastModifiedFile;
	private final boolean canSetLastModifiedFolder;
	private ConnectionDescription desc;
	private FileObject base; //FIXME FileObject is not serializable?!
	private File root;

	public CommonsVfsConnection(final ConnectionDescription desc, final FileSystem fs) throws net.sourceforge.fullsync.FileSystemException {
		try {
			this.desc = desc;
			FileSystemOptions options = new FileSystemOptions();
			if (null != fs) {
				fs.authSetup(desc, options);
			}
			base = VFS.getManager().resolveFile(desc.getUri().toString(), options);
			root = new AbstractFile(this, ".", null, true, base.exists());
			canSetLastModifiedFile = base.getFileSystem().hasCapability(Capability.SET_LAST_MODIFIED_FILE);
			canSetLastModifiedFolder = base.getFileSystem().hasCapability(Capability.SET_LAST_MODIFIED_FOLDER);
		}
		catch (FileSystemException e) {
			throw new net.sourceforge.fullsync.FileSystemException(e);
		}
	}

	@Override
	public final File createChild(final File parent, final String name, final boolean directory) throws IOException {
		return new AbstractFile(this, name, parent, directory, false);

	}

	private File buildNode(final File parent, final FileObject file) throws FileSystemException {
		String name = file.getName().getBaseName();

		File n = new AbstractFile(this, name, parent, file.getType() == FileType.FOLDER, true);
		if (file.getType() == FileType.FILE) {
			FileContent content = file.getContent();
			n.setLastModified(content.getLastModifiedTime());
			n.setSize(content.getSize());
		}
		return n;
	}

	@Override
	public final Hashtable<String, File> getChildren(final File dir) throws IOException {
		try {
			Hashtable<String, File> children = new Hashtable<String, File>();

			FileObject obj = base.resolveFile(dir.getPath());
			if (obj.exists() && (obj.getType() == FileType.FOLDER)) {
				FileObject[] list = obj.getChildren();
				for (FileObject element : list) {
					children.put(element.getName().getBaseName(), buildNode(dir, element));
				}
			}
			return children;
		}
		catch (FileSystemException fse) {
			throw new IOException(fse.getMessage());
		}
	}

	@Override
	public final boolean makeDirectory(final File dir) throws IOException {
		FileObject obj = base.resolveFile(dir.getPath());
		obj.createFolder();
		return true;
	}

	@Override
	public final boolean writeFileAttributes(final File file) throws IOException {
		FileObject obj = base.resolveFile(file.getPath());
		FileContent content = obj.getContent();
		boolean setLastModified;
		if (FileType.FOLDER == obj.getType()) {
			setLastModified = canSetLastModifiedFolder;
		}
		else {
			setLastModified = canSetLastModifiedFile;
		}
		if (setLastModified) {
			content.setLastModifiedTime(file.getLastModified());
		}
		return true;
	}

	@Override
	public final InputStream readFile(final File file) throws IOException {
		FileObject obj = base.resolveFile(file.getPath());
		return obj.getContent().getInputStream();
	}

	@Override
	public final OutputStream writeFile(final File file) throws IOException {
		FileObject obj = base.resolveFile(file.getPath());
		return obj.getContent().getOutputStream();
	}

	@Override
	public final boolean delete(final File node) throws IOException {
		FileObject obj = base.resolveFile(node.getPath());
		return obj.delete();
	}

	@Override
	public final File getRoot() {
		return root;
	}

	@Override
	public final FileObject getBase() {
		return base;
	}

	@Override
	public void flush() throws IOException {
		//FIXME: implement?
	}

	@Override
	public final void close() throws IOException {
		VFS.getManager().closeFileSystem(base.getFileSystem());
	}

	@Override
	public final boolean isCaseSensitive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public final boolean isAvailable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ConnectionDescription getConnectionDescription() {
		return desc;
	}

}
