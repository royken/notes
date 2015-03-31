select e.NOM, e.MATRICULE, n1.VALEUR as CC, n2.VALEUR as TPE, n3.VALEUR as EE
from
(select e.MATRICULE, n.VALEUR
from ETUDIANT e left join (NOTE n cross join COURS c cross JOIN EVALUATION ee) on
 n.ETUDIANT_ID =e.id and n.EVALUATION_ID =ee.id
and n.COURS_ID = c.ID and ee.CODE = 'CC') as n1,
(select e.MATRICULE, n.VALEUR
from ETUDIANT e left join (NOTE n cross join COURS c cross JOIN EVALUATION ee) on
 n.ETUDIANT_ID =e.id and n.EVALUATION_ID =ee.id
and n.COURS_ID = c.ID and ee.CODE = 'TPE') as n2,
(select e.MATRICULE, n.VALEUR
from ETUDIANT e left join (NOTE n cross join COURS c cross JOIN EVALUATION ee) on
 n.ETUDIANT_ID =e.id and n.EVALUATION_ID =ee.id
and n.COURS_ID = c.ID and ee.CODE = 'EE' and n.sessions = 1) as n3,
ETUDIANT e
where e.MATRICULE = n1.MATRICULE and e.MATRICULE = n2.MATRICULE and e.MATRICULE = n3.MATRICULE
order by e.NOM
