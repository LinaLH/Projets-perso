/**
 *
 */
package measures.units;

import java.util.NoSuchElementException;

/**
 * A Dummy implementation for {@link Unit}s.
 * Just to provide content for UI
 * @implNote TO BE REPLACED BY REAL UNITS HIERARCHY LATER
 * @author davidroussel
 */
public class DummyUnit extends Unit<Double>
{

	/**
	 * Valued constructor
	 * @param description the description of this unit
	 * @param symbol  the symbol used with this unit
	 * @throws NullPointerException  if any of the provided values are null
	 */
	public DummyUnit(String description, String symbol)
	    throws NullPointerException
	{
		super(description, symbol);
	}

	/**
	 * Internal value converted to SI value (for conversion purposes)
	 *
	 * @return the equivalent SI value
	 * @throws NoSuchElementException if there is no value to convert
	 */
	@Override
	public double getSIValue() throws NoSuchElementException {
		return 0;
	}

	/**
	 * Get the factor of this unit (for sorting purposes).
	 * This dummy implementation always return 1.0
	 * @return the factor of this unit
	 */
	@Override
	public double getFactor()
	{
		return 1.0;
	}

	/**
	 * Format the internal value for printing.
	 * @return a formatted String of the internal value
	 * @implNote In this cas no formatting is applied
	 */
	@Override
	public String formatValue()
	{
		if (!value.isEmpty())
		{
			return value.get().toString();
		}

		return "";
	}

	/**
	 * Convert value from the provided unit to this unit
	 *
	 * @param unit the unit to convert value from
	 * @throws IllegalStateException if the provided unit has no value and
	 *                               therefore no value can be converted.
	 * @implSpec {@link #value} will contain converted value from provided unit
	 * iff the provided unit has a value, otherwise
	 */
	@Override
	public void convertValueFrom(Unit<?> unit) {

	}

}
