package org.basex.query.value.node;

import static org.basex.data.DataText.*;
import static org.basex.query.QueryText.*;

import org.basex.query.value.type.*;
import org.basex.util.*;
import org.basex.util.list.*;
import org.w3c.dom.*;

/**
 * Text node fragment.
 *
 * @author BaseX Team 2005-15, BSD License
 * @author Christian Gruen
 */
public final class FTxt extends FNode {
  /**
   * Constructor.
   * @param t text value
   */
  public FTxt(final String t) {
    this(Token.token(t));
  }

  /**
   * Constructor.
   * @param t text value
   */
  public FTxt(final byte[] t) {
    super(NodeType.TXT);
    value = t;
  }

  /**
   * Constructor for creating a text from a DOM node.
   * Originally provided by Erdal Karaca.
   * @param txt DOM node
   */
  public FTxt(final Text txt) {
    this(txt.getData());
  }

  @Override
  public FNode copy() {
    return new FTxt(value).parent(parent);
  }

  @Override
  public void plan(final FElem plan) {
    addPlan(plan, planElem(VAL, value));
  }

  @Override
  public String toString() {
    final ByteList tb = new ByteList();
    for(final byte v : value) {
      if(v == '&') tb.add(E_AMP);
      else if(v == '\r') tb.add(E_CR);
    }
    return tb.add('"').toString();
  }
}
