package kz.greetgo.ekolpakov.register.impl;

import com.mongodb.client.model.Filters;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.ekolpakov.controller.model.ForumMessage;
import kz.greetgo.ekolpakov.controller.model.ForumMessagePage;
import kz.greetgo.ekolpakov.controller.model.ForumRecord;
import kz.greetgo.ekolpakov.controller.model.ForumToSave;
import kz.greetgo.ekolpakov.controller.register.AuthRegister;
import kz.greetgo.ekolpakov.controller.register.ForumRegister;
import kz.greetgo.ekolpakov.register.z_etc.MongodbSource;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static com.mongodb.client.model.Projections.include;
import static java.util.Collections.singletonList;
import static kz.greetgo.ekolpakov.register.z_etc.MongoUtil.toId;
import static kz.greetgo.ekolpakov.register.z_etc.MongoUtil.toStr;

@Bean
public class ForumRegisterImpl implements ForumRegister {

  @Override
  public ForumMessagePage getForumMessagePage(String forumCode, int offset) {
    ForumMessagePage ret = new ForumMessagePage();
    ret.list.add(message1());
    ret.list.add(message2());
    ret.list.add(message3());
    ret.list.add(message4());
    ret.currentMessageId = message4().id;
    return ret;
  }

  public BeanGetter<MongodbSource> mongodbSource;

  public BeanGetter<AuthRegister> authRegister;

  @Override
  public Optional<ForumRecord> save(ForumToSave forumToSave) {

    Document set = new Document();
    set.append("code", forumToSave.code);
    set.append("title", forumToSave.title);
    set.append("messageIdList", new ArrayList<>());

    String author = authRegister.get().currentSession().map(s -> s.personId).orElse(null);

    set.append("author", author);
    set.append("createdAt", new Date());

    Document history = new Document();
    set.append("history", history);
    setFirstHistory(history, author, "code", forumToSave.code);
    setFirstHistory(history, author, "title", forumToSave.title);

    mongodbSource.get().forum().insertOne(set);

    return loadForumRecord(toId(set.get("_id")));
  }

  private void setFirstHistory(Document history, String author, String field, Object fieldValue) {
    Document value = new Document();
    value.append("value", fieldValue);
    value.append("time", new Date());
    value.append("changer", author);

    history.append(field, singletonList(value));
  }

  private Optional<ForumRecord> loadForumRecord(ObjectId id) {

    Document first = mongodbSource.get().forum()
      .find(Filters.eq("_id", id))
      .projection(include("code", "title"))
      .limit(1)
      .first();

    if (first == null) {
      return Optional.empty();
    }

    {
      ForumRecord ret = new ForumRecord();
      ret.id = id.toHexString();
      ret.code = toStr(first.get("code"));
      ret.title = toStr(first.get("title"));
      return Optional.of(ret);
    }
  }

  private ForumMessage message1() {
    ForumMessage ret = new ForumMessage();

    ret.authorUsername = "ekolpakov";
    ret.authorImg = "/img/i-circle.png";
    ret.changedAtShort = "два дня назад";
    ret.changedAtFull = "2018-01-11 23:12";

    ret.id = "msg-17gt5ay6s7d";
    ret.number = 1;

    ret.content = "<h3>Заголовок поста форума с серьёзным обсуждением на серьёзную тему</h3>\n" +
      "      <p>\n" +
      "        И так, здесь мы будем обсуждать серьёзную тему, и наши намерения будут очень серьёзны,\n" +
      "        но иногда мы будем слегка подшучивать. Но подшучивать только слегка. На серьёзную тему\n" +
      "        нельзя серьёзно шутить - шутить надо очень аккуратно и бережно!\n" +
      "      </p>\n" +
      "      <p>\n" +
      "        Хороший серъёзный текст должен быть очень востребовательным среди людей. Ну хотелось бы\n" +
      "        так думать. Хотелось бы чтобы люди могли думать <b>на серъёзные темы</b> и обсуждать серьёзные\n" +
      "        вопросы, не переходя на личности. Более того, всякие там тролли должны, по идее, отсутствовать.\n" +
      "      </p>";

    return ret;
  }

  private ForumMessage message2() {
    ForumMessage ret = new ForumMessage();

    ret.authorUsername = "ekolpakov";
    ret.authorImg = "/img/i-circle.png";
    ret.changedAtShort = "два дня назад";
    ret.changedAtFull = "2018-01-11 23:12";

    ret.id = "msg-123df4t5";
    ret.number = 2;

    ret.content = "<p>\n" +
      "        Да. Серьёзная тема должна очень серьёзно обсуждаться.\n" +
      "      </p>\n" +
      "      <p>\n" +
      "        Замок красив как сам по себе, так и видами на него с канала и из сада, вдоволь погуляв вокруг, пообедать мы\n" +
      "        устроились в кафе на берегу с роскошным видом. Как раз переждали и небольшой дождик, глядя на замок и большую\n" +
      "        цаплю на берегу. Попробовали японское карри, темпуру из крупных креветок и сладости в виде шариков на шпажках,\n" +
      "        все было вкусно, но ничего сильно необычного или потрясающего - впечатления от французской кухни, которая\n" +
      "        вызывала культурный шок практически все время, в Японии не повторились. Еда всегда была вкусной, иногда\n" +
      "        прикольной, иногда интересной, но действительно выдающегося нам найти удалось не много (но оно конечно было).\n" +
      "        Может плохо искали, высокую кухню не пробовали, посещали разные заведения и заранее намеченные и спонтанные.\n" +
      "      </p>";

    return ret;
  }

  private ForumMessage message3() {
    ForumMessage ret = new ForumMessage();

    ret.authorUsername = "ekolpakov";
    ret.authorImg = "/img/i-circle.png";
    ret.changedAtShort = "два дня назад";
    ret.changedAtFull = "2018-01-11 23:12";

    ret.id = "msg-12w4er3sD34";
    ret.number = 3;

    ret.content = "<p>\n" +
      "        Зашли в гостиницу переобуться и отправились знакомиться с Дотонбори. С каждым шагом Осака нравилась все больше и\n" +
      "        больше, удивительно живой и прекрасный город. Посмотрели на живых фугу в аквариуме и решили попробовать говядину\n" +
      "        Кобе. Сначала было ощущение, что мы таки черезчур наивны и столик в субботу вечером надо было бы забронировать\n" +
      "        заранее, но довольно быстро нашлось заведение (Wanomiya) где нам предложили подождать 20 минут, на деле вышло\n" +
      "        даже 10 и вот мы в уютном зальчике teppanyaki ресторана, шефы - большие профи, на их работу приятно было\n" +
      "        посмотреть, говядину жарили, разделывали на кусочки и предлагали попробовать с разными сочетаниями соусов,\n" +
      "        больше всего понравилось сочетание соевого соуса и васаби. Говядина конечно отменная, мы брали сеты из середины\n" +
      "        ценового диапазона, но качество и вкус были вполне ожидаемы, откровений не случилось.\n" +
      "      </p>\n" +
      "      <p>\n" +
      "        В Осаку возвращались на \"Сакуре\" - почему то думалось, что поезд должен быть розовым - но розовым было только\n" +
      "        объявление о нем на информационном табло.\n" +
      "      </p>";

    return ret;
  }


  private ForumMessage message4() {
    ForumMessage ret = new ForumMessage();

    ret.authorUsername = "ekolpakov";
    ret.authorImg = "/img/i-circle.png";
    ret.changedAtShort = "два дня назад";
    ret.changedAtFull = "2018-01-11 23:12";

    ret.id = "msg-12w4u2u2ui4";
    ret.number = 4;

    ret.content = "<p>\n" +
      "        Музейчик маленький, но милый, самое забавное это коллекция сырья на стеллажах, дегустационный зал\n" +
      "        очаровательный, есть открытая терраса со столиками и приятным видом. Дальше было неожиданно. Дегустировать вы\n" +
      "        можете много чего. Буквально. Японской продукцией тоже дегустация не ограничена. Если кому интересно, я думаю\n" +
      "        можно весь день дегустировать, время по билету час, но никто билетами не интересовался. Цены очень гуманные\n" +
      "        (супергуманные для напитков старше 20-30 лет), мы ограничились местным ассортиментом и двумя часами, и это я еще\n" +
      "        совсем не \"виски герл\", вернее я виски обычно вообще не переношу - видимо индивидуальные особенности. Мы решили\n" +
      "        на тривиальное не размениваться и первый сет заказали от 16 лет до 21 года выдержки, и тут случилось страшное.\n" +
      "        Страшное носило имя Hibiki 21 и у меня оно вызвало взрыв эмоций близкий к эмоциям от Camus Extra Elegance, я не\n" +
      "        умею хорошо описывать ощущения такого рода, но даже если вы от виски стараетесь обычно держаться на расстоянии\n" +
      "        как минимум нескольких метров, и будет такая возможность - обязательно попробуйте это, не пожалеете, зуб даю!\n" +
      "        Муж потом заказывал и равновозрастные напитки и всю линейку Hibiki (его выбор Hibiki 30, очень мальчиковый\n" +
      "        напиток, с мужским хорактером), но для меня это все было просто \"еще один виски\". Через 2 часа, когда рюмочки\n" +
      "        уже перестали умещаться на столе (ничего страшного на самом деле, их можно просто унести на полочки для\n" +
      "        использованной посуды, но для нас это был знак, что пора бы уже заняться чем-нибудь другим), муж глянул на цены\n" +
      "        онлайн, сказал, что вкусы мои дорожают с каждой поездкой и мы отправились в магазин. Где был сюрприз номер 2.\n" +
      "        Попробовать то вы можете, а вот купить - нифига! В магазине только что-то невнятное продается - из напитков, а\n" +
      "        вот закуски очень даже рекомендую, копчености (копченые на щепках из использованных бочек из-под виски) которые\n" +
      "        мы довезли до дома, разлетелись за час, и были признаны самыми вкусными трофеями из Японии единогласно.\n" +
      "      </p>\n" +
      "      <p>\n" +
      "        История же с виски имела продолжение длиной во всю поездку, но продолжаться она начала чуть позже, а пока,\n" +
      "        слегка покачиваясь и веселясь по поводу особенностей японских виско-дегустаций мы пошли смотреть храм Shiio\n" +
      "        Shrine, который оказался очень даже симпатичным, абсолютно безлюдным и окруженным таинственно шуршащим\n" +
      "        бамбуковым лесом. Сакуры тоже присутствовали, как и другие цветущие растения. Красивое место, и прекрасно\n" +
      "        подходит для проветривания после дегустаций.\n" +
      "      </p>\n" +
      "      <p>\n" +
      "        До следующей точки на маршруте - Oyamazaki Villa Museum of Art, шли пешком. Во Франции, когда мы пошли в\n" +
      "        Оранжери смотреть кувшинки Моне, в Тюильри я совершенно забыла о существовании и музея и кувшинок (что они там в\n" +
      "        капуччино подмешивают - бог знает, будьте бдительны!), поэтому в Японии кувшинки стали Must see. Ну и вилла\n" +
      "        красиво выглядела на картинках. Ну и еще момент - была и точка номер 3 в маршруте - Sewaritei, но учитывая\n" +
      "        состояние сакурного цветения, были сомнения, что оно все еще стоит посещения, вот и подумали, что может сверху\n" +
      "        будет видно, как оно там. Рассчет оправдался полностью - туннель был отлично виден с террассы кафе и цветение\n" +
      "        там практически закончилось, грустно, но время да и деньги, добираться то пришлось бы на такси, были\n" +
      "        съэкономлены.\n" +
      "      </p>";

    return ret;
  }
}
