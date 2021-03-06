// Copyright (c) 2003 Compaq Corporation.  All rights reserved.
// Portions Copyright (c) 2003 Microsoft Corporation.  All rights reserved.

package tlc2.tool;

import tla2sany.semantic.SemanticNode;
import tlc2.TLCGlobals;
import tlc2.tool.coverage.CostModel;
import tlc2.util.Context;

public class ActionItemList {
	private static final boolean coverage = TLCGlobals.isCoverageEnabled();
	/**
	 * predicate of a conjunction
	 */
	public static final int CONJUNCT = 0;
	/**
	 * predicate
	 */
	public static final int PRED = -1;
	/**
	 * UNCHANGED predicate
	 */
	public static final int UNCHANGED = -2;
	/**
	 * pred' # pred
	 */
	public static final int CHANGED = -3;
	
  /**
   * We assume that this.pred is null iff the list is empty.
   */
  public final SemanticNode pred;     // Expression of the action
  public final Context con;           // Context of the action
  private final int kind;  
  public final ActionItemList next;
  public final CostModel cm;

  public final static ActionItemList
    Empty = new ActionItemList(null, null, 0, null, null);
  
  /* Constructors */
  private ActionItemList(SemanticNode pred, Context con,
			 int kind, ActionItemList next, CostModel cm) {
    this.pred = pred;
    this.con = con;
    this.kind = kind;
    this.next = next;
    this.cm = cm;
  }

  public final SemanticNode carPred() { return this.pred; }

  public final Context carContext() { return this.con; }

  /**
   * The meaning of this.kind is given as follows:
   *    kind > 0:  pred of a conjunction
   *    kind = -1: pred
   *    kind = -2: UNCHANGED pred
   *    kind = -3: pred' # pred
   */
  public final int carKind() { return this.kind; }

  public final ActionItemList cdr() { return this.next; }

  public final ActionItemList cons(SemanticNode pred,
				   Context con, CostModel cm, int kind) {
    return new ActionItemList(pred, con, kind, this, coverage ? cm.get(pred) : cm);
  }

  public final boolean isEmpty() { return this == Empty; }
  
}
