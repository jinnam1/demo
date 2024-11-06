package com.kh.demo.domain.product.dao;

import com.kh.demo.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Repository
@RequiredArgsConstructor  // final 필드를 매개값으로 갖는 생성자를 자동 생성해준다.

public class ProductDAOImpl implements ProductDAO{

  private final NamedParameterJdbcTemplate template;

//  public ProductDAOImpl(NamedParameterJdbcTemplate template){
//    this.template = template;
//  }

  @Override
  public Long save(Product product) {
    // sql
    StringBuffer sql = new StringBuffer();
    sql.append("insert into product(product_id,pname,quantity,price) ");
    sql.append("values(product_product_id_seq.nextval, :pname, :quantity, :price) ");

    // sql 수행
//    Map param = Map.of("pname", product.getPname(), "quantity", product.getQuantity(), "price", product.getPrice());

    // spring bean 객체(=product)의 멤버필드와 sql의 매개변수(=파라미터) 이름을 매칭한 정보를 반환
    SqlParameterSource param = new BeanPropertySqlParameterSource(product);

    // template.update()가 수행된 레코드의 특정 컬럼값을 읽어오는 용도
    KeyHolder keyHolder = new GeneratedKeyHolder();
    long rows  = template.update(sql.toString(),param,keyHolder,new String[]{"product_id"});

    log.info("rows ={}", rows);

    // case 1) 1개의 컬럼값만 읽어올 때
//    Long pid = keyHolder.getKey().longValue();    // 삼품 아이디

    // case 2) 2개 이상의 컬럼값을 읽어올때

    Number pidNumber = (Number)keyHolder.getKeys().get("product_id");
    Long pid = pidNumber.longValue();

    return pid;  // 상품 아이디 반환
  }

//  RowMapper<Product> myRowMapper = new RowMapper<>(){
//
//    @Override
//    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
//      Product product = new Product();
//      product.setProductId(rs.getLong("product_id"));
//      product.setPname(rs.getString("pname"));
//      product.setPrice(rs.getLong("price"));
//      product.setQuantity(rs.getLong("quantity"));
//      return product;
//    }
//  };

  // 위의 주석 표현된 수동 매핑 방법을 람다식으로 표현한것
  // 람다식은 인터페이스에 하나의 메소드 밖에 없을때 즉, functionalInterface 일때 사용 가능한거 같다

  RowMapper<Product> myRowMapper = (rs, rowNum) -> {
      Product product = new Product();
      product.setProductId(rs.getLong("product_id"));
      product.setPname(rs.getString("pname"));
      product.setPrice(rs.getLong("price"));
      product.setQuantity(rs.getLong("quantity"));
      return product;
  };



  RowMapper<Product> productRowMapper(){
    return (rs, rowNum) -> {
      Product product = new Product();
      product.setProductId(rs.getLong("product_id"));
      product.setPname(rs.getString("pname"));
      product.setPrice(rs.getLong("price"));
      product.setQuantity(rs.getLong("quantity"));
      return product;
    };
  }




  @Override
  public List<Product> findAll() {
    //sql

    StringBuffer sql = new StringBuffer();
    sql.append("select product_id, pname, quantity, price ");
    sql.append("    from product ");
    sql.append("    order by product_id desc ");

    //db요청
    //BeanPropertyRowMapper : 자바 entity클래스와 db레코드를 자동 매핑
//    List<Product> list = template.query(sql.toString(), BeanPropertyRowMapper.newInstance(Product.class));

    // 바로 위에 있는 RowMapper의 익명클래스로 인해서 엔티티 클래스와 sql레코드를 수동매핑
    // 수동 매핑 익명클래스는 한번 만 쓸 경우 코드가 길어 지더라도 지금의 myRowMapper 변수 자리에 코드 자체를 통째로 넣어도된다
    List<Product> list = template.query(sql.toString(), productRowMapper());

    return list;
  }

  @Override
  public Optional<Product> findByID(Long productId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select product_id, pname, quantity, price ");
    sql.append("    from product ");
    sql.append("    where product_id = :productId ");

    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("productId", productId);
    Product product = null;
    try {
      product = template.queryForObject(
              sql.toString(),
              param,
              BeanPropertyRowMapper.newInstance(Product.class));
    }catch (EmptyResultDataAccessException e){  // 조회 레코드가 없으면 예외 발생
      return Optional.empty();
    }
    return Optional.of(product);
  }

  @Override
  public int deleteById(Long productId) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from product ");
    sql.append("    where product_id = :productId ");

    Map<String,Long> param = Map.of("productId",productId);
    int rows = template.update(sql.toString(), param);

    return rows;
  }

  @Override
  public int updateById(Long productId, Product product) {

    // sql
    StringBuffer sql = new StringBuffer();
    sql.append("update product ");
    sql.append("    set pname = :pname , quantity = :quantity , price = :price ");
    sql.append("    where product_id = :productId ");

    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("pname",product.getPname())
            .addValue("quantity",product.getQuantity())
            .addValue("price",product.getPrice())
            .addValue("productId",productId);


    int rows = template.update(sql.toString(), param);

    return rows;
  }


  @Override
  public int deleteByIds(List<Long> productIds) {
    StringBuffer sql = new StringBuffer();

    sql.append("delete from product ");
    sql.append("where product_id in (:productIds) ");


    Map<String, List<Long>> param = Map.of("productIds", productIds);
    int rows = template.update(sql.toString(), param);



    return rows;
  }
}
