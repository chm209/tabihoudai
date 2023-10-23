package com.tabihoudai.tabihoudai_api.repository.plan;

import java.util.List;

public interface PlanRepositoryCustom {

    List<Object[]> getBestPlan();
    public List<Object[]> getAreaBestPlan(Integer area);
}
