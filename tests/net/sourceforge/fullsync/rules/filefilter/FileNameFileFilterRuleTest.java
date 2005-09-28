/*
 * Created on May 28, 2005
 */
package net.sourceforge.fullsync.rules.filefilter;

import junit.framework.TestCase;
import net.sourceforge.fullsync.rules.filefilter.values.TextValue;

/**
 * @author Michele Aiello
 */
public class FileNameFileFilterRuleTest extends TestCase {

	public void testOpIs() {
		FileNameFileFilterRule filterRule = new FileNameFileFilterRule(new TextValue("foobar.txt"), FileNameFileFilterRule.OP_IS);
		assertTrue(filterRule.match(new TestNode("foobar.txt", "/root/foobar.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("afoobar.txt", "/root/afoobar.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("foobar.txta", "/root/foobar.txta", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("foobara.txt", "/root/foobara.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("fooba.txt", "/root/fooba.txt", true, false, 0, 0)));
	}

	public void testOpIsnt() {
		FileNameFileFilterRule filterRule = new FileNameFileFilterRule(new TextValue("foobar.txt"), FileNameFileFilterRule.OP_ISNT);
		assertTrue(!filterRule.match(new TestNode("foobar.txt", "/root/foobar.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("afoobar.txt", "/root/afoobar.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("foobar.txta", "/root/foobar.txta", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("foobara.txt", "/root/foobara.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("fooba.txt", "/root/fooba.txt", true, false, 0, 0)));
	}

	public void testOpContains() {
		FileNameFileFilterRule filterRule = new FileNameFileFilterRule(new TextValue("bar"), FileNameFileFilterRule.OP_CONTAINS);
		assertTrue(filterRule.match(new TestNode("foobar.txt", "/root/foobar.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("afoobar.txt", "/root/afoobar.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("foobar.txta", "/root/foobar.txta", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("foobara.txt", "/root/foobara.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("fooba.txt", "/root/fooba.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("foobsasr.txt", "/root/foobsasr.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("foobasr.txt", "/root/foobasr.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("foorab.txt", "/root/foorab.txt", true, false, 0, 0)));
	}

	public void testOpDoesntContains() {
		FileNameFileFilterRule filterRule = new FileNameFileFilterRule(new TextValue("bar"), FileNameFileFilterRule.OP_DOESNT_CONTAINS);
		assertTrue(!filterRule.match(new TestNode("foobar.txt", "/root/foobar.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("afoobar.txt", "/root/afoobar.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("foobar.txta", "/root/foobar.txta", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("foobara.txt", "/root/foobara.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("fooba.txt", "/root/fooba.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("foobsasr.txt", "/root/foobsasr.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("foobasr.txt", "/root/foobasr.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("foorab.txt", "/root/foorab.txt", true, false, 0, 0)));
	}

	public void testOpBeginsWith() {
		FileNameFileFilterRule filterRule = new FileNameFileFilterRule(new TextValue("foo"), FileNameFileFilterRule.OP_BEGINS_WITH);
		assertTrue(filterRule.match(new TestNode("foobar.txt", "/root/foobar.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("afoobar.txt", "/root/afoobar.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("foobar.txta", "/root/foobar.txta", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("foobara.txt", "/root/foobara.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("fooba.txt", "/root/fooba.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("foboar.txt", "/root/foboar.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("oofbar.txt", "/root/oofbar.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("foo", "/root/foo", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("foo.", "/root/foo.", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("foo.txt", "/root/foo.txt", true, false, 0, 0)));
	}

	public void testOpBeginsWith2() {
		FileNameFileFilterRule filterRule = new FileNameFileFilterRule(new TextValue(".foo"), FileNameFileFilterRule.OP_BEGINS_WITH);
		assertTrue(filterRule.match(new TestNode(".foobar.txt", "/root/.foobar.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("a.foobar.txt", "/root/a.foobar.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode(".foobar.txta", "/root/.foobar.txta", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode(".foobara.txt", "/root/.foobara.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode(".fooba.txt", "/root/.fooba.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode(".foboar.txt", "/root/.foboar.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode(".oofbar.txt", "/root/.oofbar.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode(".foo", "/root/.foo", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode(".foo.txt", "/root/.foo.txt", true, false, 0, 0)));
	}

	public void testOpDoesntBeginsWith() {
		FileNameFileFilterRule filterRule = new FileNameFileFilterRule(new TextValue("foo"), FileNameFileFilterRule.OP_DOESNT_BEGINS_WITH);
		assertTrue(!filterRule.match(new TestNode("foobar.txt", "/root/foobar.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("afoobar.txt", "/root/afoobar.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("foobar.txta", "/root/foobar.txta", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("foobara.txt", "/root/foobara.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("fooba.txt", "/root/fooba.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("foboar.txt", "/root/foboar.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("oofbar.txt", "/root/oofbar.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("foo", "/root/foo", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("foo.", "/root/foo.", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("foo.txt", "/root/foo.txt", true, false, 0, 0)));
	}

	public void testOpEndsWith() {
		FileNameFileFilterRule filterRule = new FileNameFileFilterRule(new TextValue("txt"), FileNameFileFilterRule.OP_ENDS_WITH);
		assertTrue(filterRule.match(new TestNode("foobar.txt", "/root/foobar.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("afoobar.atxt", "/root/afoobar.atxt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("foobar.txta", "/root/foobar.txta", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("foobara.txt", "/root/foobara.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("fooba.txt", "/root/fooba.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("foboar.ttxt", "/root/foboar.ttxt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("oofbar.xt", "/root/oofbar.xt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("foo", "/root/foo", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("txt", "/root/txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode(".txt", "/root/.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("txt.", "/root/txt.", true, false, 0, 0)));
	}

	public void testOpEndsWith2() {
		FileNameFileFilterRule filterRule = new FileNameFileFilterRule(new TextValue(".txt"), FileNameFileFilterRule.OP_ENDS_WITH);
		assertTrue(filterRule.match(new TestNode("foobar.txt", "/root/foobar.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("afoobar.atxt", "/root/afoobar.atxt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("foobar.txta", "/root/foobar.txta", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("foobara.txt", "/root/foobara.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("fooba.txt", "/root/fooba.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("foboar.ttxt", "/root/foboar.ttxt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("oofbar.xt", "/root/oofbar.xt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("foo", "/root/foo", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("txt", "/root/txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode(".txt", "/root/.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("txt.", "/root/txt.", true, false, 0, 0)));
	}

	public void testOpDoesntEndsWith() {
		FileNameFileFilterRule filterRule = new FileNameFileFilterRule(new TextValue("txt"), FileNameFileFilterRule.OP_DOESNT_ENDS_WITH);
		assertTrue(!filterRule.match(new TestNode("foobar.txt", "/root/foobar.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("afoobar.atxt", "/root/afoobar.atxt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("foobar.txta", "/root/foobar.txta", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("foobara.txt", "/root/foobara.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("fooba.txt", "/root/fooba.txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("foboar.ttxt", "/root/foboar.ttxt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("oofbar.xt", "/root/oofbar.xt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("foo", "/root/foo", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("txt", "/root/txt", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode(".txt", "/root/.txt", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("txt.", "/root/txt.", true, false, 0, 0)));
	}

	public void testOpRegExp() {
		FileNameFileFilterRule filterRule = new FileNameFileFilterRule(new TextValue(".+\\.gif"), FileNameFileFilterRule.OP_MATCHES_REGEXP);
		assertTrue(filterRule.match(new TestNode("foobar.gif", "/root/foobar.gif", true, false, 0, 0)));
		assertTrue(filterRule.match(new TestNode("foobara.gif", "/root/foobara.gif", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("gif", "/root/gif", true, false, 0, 0)));
		assertTrue(!filterRule.match(new TestNode("foobar.jpg", "/root/foobar.jpg", true, false, 0, 0)));
	}
}
