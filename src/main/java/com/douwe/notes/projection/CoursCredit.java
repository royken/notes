package com.douwe.notes.projection;

import com.douwe.notes.entities.Cours;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class CoursCredit {
    
    private Cours cours;
    
    private Integer credit;

    public CoursCredit(Cours cours, Integer credit) {
        this.cours = cours;
        this.credit = credit;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }
}
