package at.technikum.se14m009.generated;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import at.technikum.se14m009.generated.MovieEntity;
import at.technikum.se14m009.generated.SearchEntity;

import at.technikum.se14m009.generated.MovieEntityDao;
import at.technikum.se14m009.generated.SearchEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig movieEntityDaoConfig;
    private final DaoConfig searchEntityDaoConfig;

    private final MovieEntityDao movieEntityDao;
    private final SearchEntityDao searchEntityDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        movieEntityDaoConfig = daoConfigMap.get(MovieEntityDao.class).clone();
        movieEntityDaoConfig.initIdentityScope(type);

        searchEntityDaoConfig = daoConfigMap.get(SearchEntityDao.class).clone();
        searchEntityDaoConfig.initIdentityScope(type);

        movieEntityDao = new MovieEntityDao(movieEntityDaoConfig, this);
        searchEntityDao = new SearchEntityDao(searchEntityDaoConfig, this);

        registerDao(MovieEntity.class, movieEntityDao);
        registerDao(SearchEntity.class, searchEntityDao);
    }
    
    public void clear() {
        movieEntityDaoConfig.getIdentityScope().clear();
        searchEntityDaoConfig.getIdentityScope().clear();
    }

    public MovieEntityDao getMovieEntityDao() {
        return movieEntityDao;
    }

    public SearchEntityDao getSearchEntityDao() {
        return searchEntityDao;
    }

}