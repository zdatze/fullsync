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
package net.sourceforge.fullsync.rules.filefilter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import net.sourceforge.fullsync.fs.File;

public class TestNode implements File {
	private static final long serialVersionUID = 2L;

	private String name;
	private String path;
	private boolean directory;
	private boolean exists;
	private long lastModified;
	private long size;

	public TestNode(String name, String path, boolean exists, boolean directory, long length, long lm) {
		this.name = name;
		this.path = path;
		this.exists = exists;
		this.directory = directory;
		this.lastModified = lm;
		this.size = length;
	}

	public File getDirectory() {
		return null;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return null;
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return null;
	}

	@Override
	public File getParent() {
		return null;
	}

	@Override
	public Collection<File> getChildren() {
		return null;
	}

	@Override
	public File getChild(String name) {
		return null;
	}

	public File createDirectory(String name) {
		return null;
	}

	public File createFile(String name) {
		return null;
	}

	@Override
	public boolean makeDirectory() {
		return true;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public boolean isDirectory() {
		return directory;
	}

	@Override
	public boolean exists() {
		return exists;
	}

	@Override
	public boolean isBuffered() {
		return false;
	}

	@Override
	public File getUnbuffered() {
		return null;
	}

	@Override
	public boolean delete() {
		return false;
	}

	@Override
	public void refresh() {

	}

	@Override
	public void refreshBuffer() {
	}

	@Override
	public File createChild(String name, boolean directory) {
		return null;
	}

	@Override
	public boolean isFile() {
		return !directory;
	}

	@Override
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public void writeFileAttributes() throws IOException {

	}

	public void setDirectory(boolean directory) {
		this.directory = directory;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		if (!exists) {
			return "not exists";
		}
		else if (directory) {
			return "Directory";
		}
		else {
			return "File (" + size + "," + lastModified + ")";
		}
	}

	@Override
	public long getLastModified() {
		return lastModified;
	}

	@Override
	public long getSize() {
		return size;
	}

	@Override
	public void setSize(long size) {
		this.size = size;
	}
}
