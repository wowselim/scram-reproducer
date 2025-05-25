package co.selim.scram_reproducer;

import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlConnectOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ExtendWith(VertxExtension.class)
public class TestMainVerticle {

  @Container
  private final JdbcDatabaseContainer<?> pgContainer = new PostgreSQLContainer<>("postgres:17-alpine");
  private Pool pgPool;

  @BeforeEach
  void init(Vertx vertx) {
    SqlConnectOptions connectOptions = PgConnectOptions.fromUri(pgContainer.getJdbcUrl().substring("jdbc:".length()))
      .setUser(pgContainer.getUsername())
      .setPassword(pgContainer.getPassword());
    pgPool = Pool.pool(vertx, connectOptions, new PoolOptions());
  }

  @Test
  void testSelectOne(Vertx vertx, VertxTestContext testContext) {
    pgPool.query("SELECT 1")
      .execute()
      .onSuccess(rows -> testContext.completeNow())
      .onFailure(testContext::failNow);
  }
}
