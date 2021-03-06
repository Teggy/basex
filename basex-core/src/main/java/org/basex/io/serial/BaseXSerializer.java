package org.basex.io.serial;

import java.io.*;

import org.basex.io.out.*;
import org.basex.query.*;
import org.basex.query.value.array.*;
import org.basex.query.value.item.*;
import org.basex.query.value.map.*;

/**
 * This class serializes items in a project-specific mode.
 *
 * @author BaseX Team 2005-15, BSD License
 * @author Christian Gruen
 */
public final class BaseXSerializer extends AdaptiveSerializer {
  /** Binary. */
  private boolean binary;
  /** Level counter. */
  private int count;

  /**
   * Constructor, specifying serialization options.
   * @param os output stream
   * @param sopts serialization parameters
   * @throws IOException I/O exception
   */
  protected BaseXSerializer(final OutputStream os, final SerializerOptions sopts)
      throws IOException {
    super(os, sopts);
    binary = sopts.yes(SerializerOptions.BINARY);
  }

  @Override
  protected void atomic(final Item it) throws IOException {
    if(count == 0) {
      try {
        if(binary && it instanceof Bin) {
          try(final InputStream is = it.input(null)) {
            final PrintOutput po = out;
            for(int b; (b = is.read()) != -1;) po.write(b);
          }
        } else {
          printChars(it.string(null));
        }
      } catch(final QueryException ex) {
        throw new QueryIOException(ex);
      }
    } else {
      super.atomic(it);
    }
  }

  @Override
  protected void array(final Array item) throws IOException {
    ++count;
    super.array(item);
    --count;
  }

  @Override
  protected void map(final Map item) throws IOException {
    ++count;
    super.map(item);
    --count;
  }
}
