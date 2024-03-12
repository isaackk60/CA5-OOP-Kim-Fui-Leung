package org.ca5.Exceptions;
/**

 * Author:  Aoife Murphy
 * Other contributors:  Kim Fui Leung, Jamie Duffy Creagh
 * Date: 9-03-24

 */

import java.sql.SQLException;

public class DaoException extends SQLException
{
    public DaoException()
    {
        // not used
    }

    public DaoException(String aMessage)
    {
        super(aMessage);
    }
}
