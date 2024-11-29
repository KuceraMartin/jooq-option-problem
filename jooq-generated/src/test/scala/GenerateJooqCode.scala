import com.dimafeng.testcontainers._
import org.jooq.Converter
import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb._
import org.slf4j.LoggerFactory
import org.testcontainers.containers.wait.strategy.Wait

import java.io.File
import scala.reflect.ClassTag
import scala.util.Using

object GenerateJooqCode {

  private val logger = LoggerFactory.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    val inRootDirectory = new File("docker-compose.yml").exists()
    val targetDirectory = if (inRootDirectory) "jooq-generated/src/main/scala" else "src/main/scala"

    logger.info(s"Generating jOOQ Scala code into $targetDirectory")

    val dockerComposeFile = if (inRootDirectory) new File("docker-compose.yml") else new File("../docker-compose.yml")
    val container = new DockerComposeContainer(
      composeFiles = dockerComposeFile,
      exposedServices = List(ExposedService("postgres", 5432)),
      waitingFor = Some(WaitingForService("migration", Wait.forLogMessage(".*Successfully applied.*", 1)))
    )

    container.start()

    Using.resource(container) { c =>
      val port = c.getServicePort("postgres", 5432)
      GenerationTool
        .generate(
          new Configuration()
            .withJdbc(
              new Jdbc()
                .withDriver("org.postgresql.Driver")
                .withUrl(s"jdbc:postgresql://localhost:$port/localdb?currentSchema=test")
                .withUser("localuser")
                .withPassword("localpass")
            )
            .withGenerator(
              new Generator()
                .withName("org.jooq.codegen.ScalaGenerator")
                .withDatabase(
                  new Database()
                    .withName("org.jooq.meta.postgres.PostgresDatabase")
                    .withInputSchema("test")
                    .withForcedTypes(
                      new ForcedType()
                        .withUserType("scala.Option[Int]")
                        .withIncludeTypes("integer")
                        .withConverter("converters.OptionIntConverter")
                        .withNullability(Nullability.NULL)
                    )
                )
                .withTarget(
                  new Target()
                    .withPackageName("generated.jooq")
                    .withDirectory(targetDirectory)
                )
            )
        )
    }
  }

}
