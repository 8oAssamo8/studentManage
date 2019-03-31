package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Ranking;
import dao.DatabaseDao;
import dao.RankingDao;

public class RankingService {
	public List<Ranking> getRankingList(String classId, String terms) {
		List<Ranking> rankings = new ArrayList<Ranking>();
		try {
			DatabaseDao databaseDao = new DatabaseDao();
			RankingDao rankingDao = new RankingDao();
			rankings = rankingDao.getRankingList(classId, terms, databaseDao);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rankings;
	}

	public void computeRankingList(List<Ranking> rankings) {
		Integer lastScore = -1, nowRanking = 1, count;
		for (count = 0; count < rankings.size(); count++) {
			if (lastScore == rankings.get(count).getSumScore()) {
				rankings.get(count).setRanking(nowRanking);
			} else {
				nowRanking = count + 1;
				rankings.get(count).setRanking(nowRanking);
			}
			lastScore = rankings.get(count).getSumScore();
		}
	}
}
