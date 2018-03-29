package br.les.opus.dengue.core.repositories;


import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.dengue.core.fields.Field;

@Repository
public class FieldRepository extends HibernateAbstractRepository<Field, Long>{

}
