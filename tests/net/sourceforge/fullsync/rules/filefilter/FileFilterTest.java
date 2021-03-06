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
/*
 * Created on May 28, 2005
 */
package net.sourceforge.fullsync.rules.filefilter;

import static org.junit.Assert.assertTrue;
import net.sourceforge.fullsync.fs.File;
import net.sourceforge.fullsync.rules.filefilter.values.OperandValue;
import net.sourceforge.fullsync.rules.filefilter.values.SizeValue;
import net.sourceforge.fullsync.rules.filefilter.values.TextValue;

import org.junit.Test;

public class FileFilterTest {

	private static class AlwaysTrueFileFilterRule extends FileFilterRule {
		private static final long serialVersionUID = 2L;

		@Override
		public boolean match(File file) {
			return true;
		}

		@Override
		public String toString() {
			return "TRUE";
		}

		@Override
		public int getOperator() {
			return 0;
		}

		@Override
		public String getOperatorName() {
			return null;
		}

		@Override
		public String getRuleType() {
			return "True";
		}

		@Override
		public OperandValue getValue() {
			return null;
		}
	}

	private static class AlwaysFalseFileFilterRule extends FileFilterRule {
		private static final long serialVersionUID = 2L;

		@Override
		public boolean match(File file) {
			return false;
		}

		@Override
		public String toString() {
			return "FALSE";
		}

		@Override
		public int getOperator() {
			return 0;
		}

		@Override
		public String getOperatorName() {
			return null;
		}

		@Override
		public String getRuleType() {
			return "False";
		}

		@Override
		public OperandValue getValue() {
			return null;
		}
	}

	@Test
	public void testEmptyFilter() {
		FileFilter filter = new FileFilter();
		filter.setMatchType(FileFilter.MATCH_ALL);
		assertTrue(filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setMatchType(FileFilter.MATCH_ANY);
		assertTrue(filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));
	}

	@Test
	public void testOneRuleFilter() {
		FileFilter filter = new FileFilter();
		filter.setMatchType(FileFilter.MATCH_ALL);
		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysTrueFileFilterRule() });

		assertTrue(filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setMatchType(FileFilter.MATCH_ANY);
		assertTrue(filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysFalseFileFilterRule() });

		assertTrue(!filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setMatchType(FileFilter.MATCH_ANY);
		assertTrue(!filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));
	}

	@Test
	public void testFilterAllBasic() {
		FileFilter filter = new FileFilter();
		filter.setMatchType(FileFilter.MATCH_ALL);

		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysTrueFileFilterRule(), new AlwaysTrueFileFilterRule() });
		assertTrue(filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysFalseFileFilterRule(), new AlwaysTrueFileFilterRule() });
		assertTrue(!filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysTrueFileFilterRule(), new AlwaysFalseFileFilterRule() });
		assertTrue(!filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysFalseFileFilterRule(), new AlwaysFalseFileFilterRule() });
		assertTrue(!filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysTrueFileFilterRule(), new AlwaysTrueFileFilterRule(),
				new AlwaysTrueFileFilterRule(), new AlwaysTrueFileFilterRule(), new AlwaysTrueFileFilterRule(),
				new AlwaysTrueFileFilterRule(), new AlwaysTrueFileFilterRule() });
		assertTrue(filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysTrueFileFilterRule(), new AlwaysFalseFileFilterRule(),
				new AlwaysFalseFileFilterRule(), new AlwaysFalseFileFilterRule(), new AlwaysFalseFileFilterRule(),
				new AlwaysFalseFileFilterRule(), new AlwaysFalseFileFilterRule() });
		assertTrue(!filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysTrueFileFilterRule(), new AlwaysTrueFileFilterRule(),
				new AlwaysFalseFileFilterRule(), new AlwaysFalseFileFilterRule(), new AlwaysFalseFileFilterRule(),
				new AlwaysFalseFileFilterRule(), new AlwaysFalseFileFilterRule() });
		assertTrue(!filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysTrueFileFilterRule(), new AlwaysTrueFileFilterRule(),
				new AlwaysFalseFileFilterRule(), new AlwaysTrueFileFilterRule(), new AlwaysFalseFileFilterRule(),
				new AlwaysFalseFileFilterRule(), new AlwaysFalseFileFilterRule() });
		assertTrue(!filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysTrueFileFilterRule(), new AlwaysTrueFileFilterRule(),
				new AlwaysFalseFileFilterRule(), new AlwaysTrueFileFilterRule(), new AlwaysFalseFileFilterRule(),
				new AlwaysFalseFileFilterRule(), new AlwaysTrueFileFilterRule() });
		assertTrue(!filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysTrueFileFilterRule(), new AlwaysTrueFileFilterRule(),
				new AlwaysTrueFileFilterRule(), new AlwaysTrueFileFilterRule(), new AlwaysTrueFileFilterRule(),
				new AlwaysFalseFileFilterRule(), new AlwaysTrueFileFilterRule() });
		assertTrue(!filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysTrueFileFilterRule(), new AlwaysFalseFileFilterRule(),
				new AlwaysTrueFileFilterRule(), new AlwaysTrueFileFilterRule(), new AlwaysTrueFileFilterRule(),
				new AlwaysTrueFileFilterRule(), new AlwaysTrueFileFilterRule() });
		assertTrue(!filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

	}

	@Test
	public void testFilterAnyBasic() {
		FileFilter filter = new FileFilter();
		filter.setMatchType(FileFilter.MATCH_ANY);

		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysTrueFileFilterRule(), new AlwaysTrueFileFilterRule() });
		assertTrue(filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysFalseFileFilterRule(), new AlwaysTrueFileFilterRule() });
		assertTrue(filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysTrueFileFilterRule(), new AlwaysFalseFileFilterRule() });
		assertTrue(filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysFalseFileFilterRule(), new AlwaysFalseFileFilterRule() });
		assertTrue(!filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysTrueFileFilterRule(), new AlwaysTrueFileFilterRule(),
				new AlwaysTrueFileFilterRule(), new AlwaysTrueFileFilterRule(), new AlwaysTrueFileFilterRule(),
				new AlwaysTrueFileFilterRule(), new AlwaysTrueFileFilterRule() });
		assertTrue(filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysFalseFileFilterRule(), new AlwaysFalseFileFilterRule(),
				new AlwaysTrueFileFilterRule(), new AlwaysFalseFileFilterRule(), new AlwaysFalseFileFilterRule(),
				new AlwaysFalseFileFilterRule(), new AlwaysFalseFileFilterRule() });
		assertTrue(filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysFalseFileFilterRule(), new AlwaysFalseFileFilterRule(),
				new AlwaysFalseFileFilterRule(), new AlwaysFalseFileFilterRule(), new AlwaysFalseFileFilterRule(),
				new AlwaysTrueFileFilterRule(), new AlwaysFalseFileFilterRule() });
		assertTrue(filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysFalseFileFilterRule(), new AlwaysFalseFileFilterRule(),
				new AlwaysTrueFileFilterRule(), new AlwaysFalseFileFilterRule(), new AlwaysFalseFileFilterRule(),
				new AlwaysTrueFileFilterRule(), new AlwaysFalseFileFilterRule() });
		assertTrue(filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));

		filter.setFileFilterRules(new FileFilterRule[] { new AlwaysFalseFileFilterRule(), new AlwaysFalseFileFilterRule(),
				new AlwaysFalseFileFilterRule(), new AlwaysFalseFileFilterRule(), new AlwaysFalseFileFilterRule(),
				new AlwaysFalseFileFilterRule(), new AlwaysFalseFileFilterRule() });
		assertTrue(!filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));
	}

	@Test
	public void testFilterInclude() {
		FileFilter filter = new FileFilter();
		filter.setMatchType(FileFilter.MATCH_ANY);
		filter.setFilterType(FileFilter.INCLUDE);
		filter.setFileFilterRules(new FileFilterRule[] {
				new FileNameFileFilterRule(new TextValue(".txt"), FileNameFileFilterRule.OP_ENDS_WITH),
				new FileSizeFileFilterRule(new SizeValue("1024 Bytes"), FileSizeFileFilterRule.OP_IS_LESS_THAN) });

		assertTrue(filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));
		assertTrue(filter.match(new TestNode("foobar.txt.", "somedir/foobar.txt", true, false, 0, 0)));
		assertTrue(!filter.match(new TestNode("foobar.txt.", "somedir/foobar.txt", true, false, 2048, 0)));
	}

	@Test
	public void testFilterExclude() {
		FileFilter filter = new FileFilter();
		filter.setMatchType(FileFilter.MATCH_ANY);
		filter.setFilterType(FileFilter.EXCLUDE);
		filter.setFileFilterRules(new FileFilterRule[] {
				new FileNameFileFilterRule(new TextValue(".txt"), FileNameFileFilterRule.OP_ENDS_WITH),
				new FileSizeFileFilterRule(new SizeValue("1024 Bytes"), FileSizeFileFilterRule.OP_IS_LESS_THAN) });

		assertTrue(!filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));
		assertTrue(!filter.match(new TestNode("foobar.txt.", "somedir/foobar.txt", true, false, 0, 0)));
		assertTrue(filter.match(new TestNode("foobar.txt.", "somedir/foobar.txt", true, false, 2048, 0)));
	}

	// public void testSerialization() {
	// FileFilterManager fileFilterManager = new FileFilterManager();
	//
	// File filterFile = new File("filter.xml");
	// filterFile.deleteOnExit();
	// try {
	// DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	// Document doc = docBuilder.newDocument();
	//
	// FileFilter filter = new FileFilter();
	// filter.setMatchType(FileFilter.MATCH_ANY);
	// filter.setFileFilterRules(new FileFilterRule[] {new FileNameFileFilterRule(".txt", FileNameFileFilterRule.OP_ENDS_WITH),
	// new FileSizeFileFilterRule(1024, FileSizeFileFilterRule.OP_IS_LESS_THAN)});
	//
	// assertTrue(filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));
	// assertTrue(filter.match(new File("somedir/foobar.txt")));
	//
	// System.out.println("Serializzato:");
	// System.out.println(filter.toString());
	//
	// fileFilterManager.serializeFileFilter(filter, doc);
	//
	// OutputStream out = new FileOutputStream(filterFile);
	//
	// OutputFormat format = new OutputFormat(doc, "UTF-8", true);
	// XMLSerializer serializer = new XMLSerializer (out, format);
	// serializer.asDOMSerializer();
	// serializer.serialize(doc);
	// } catch( Exception e ) {
	// e.printStackTrace();
	// }
	//
	// try {
	// DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	// Document doc = builder.parse(filterFile);
	//
	// FileFilter filter = fileFilterManager.unserializeFileFilter(doc.getDocumentElement());
	//
	// assertTrue(filter.match(new TestNode("foobar.txt", "foobar.txt", true, false, 0, 0)));
	// assertTrue(filter.match(new File("somedir/foobar.txt")));
	//
	// System.out.println("Deserializzato:");
	// System.out.println(filter.toString());
	// } catch( Exception e ) {
	// e.printStackTrace();
	// }
	//
	// }

}
