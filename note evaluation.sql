select e.NOM, n.VALEUR
from ETUDIANT e left join (NOTE n cross join COURS c cross JOIN EVALUATION ee) on
 n.ETUDIANT_ID =e.id and n.EVALUATION_ID =ee.id
and n.COURS_ID = c.ID and ee.CODE = 'EE' and n.sessions = 1
