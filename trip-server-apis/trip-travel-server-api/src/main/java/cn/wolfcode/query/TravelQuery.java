package cn.wolfcode.query;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wby
 * @version 1.0
 * @date 2022-12-27 027 15:50
 */
@Getter
@Setter
public class TravelQuery extends BaseQuery {

    private static final Map<Integer, TravelRange> TRAVEL_TIME_MAP = new HashMap<>();
    private static final Map<Integer, TravelRange> AVG_CONSUME_MAP = new HashMap<>();
    private static final Map<Integer, TravelRange> DAY_MAP = new HashMap<>();

    private Long destId;
    private String orderBy = "viewnum";
    private TravelRange travelTimeType;
    private TravelRange consumeType;
    private TravelRange dayType;

    static {
        // 旅游时间
        TRAVEL_TIME_MAP.put(1, new TravelRange(1, 2));
        TRAVEL_TIME_MAP.put(2, new TravelRange(3, 4));
        TRAVEL_TIME_MAP.put(3, new TravelRange(5, 6));
        TRAVEL_TIME_MAP.put(4, new TravelRange(7, 8));
        TRAVEL_TIME_MAP.put(5, new TravelRange(9, 10));
        TRAVEL_TIME_MAP.put(6, new TravelRange(11, 12));

        // 出行天数
        DAY_MAP.put(1, new TravelRange(1, 3));
        DAY_MAP.put(2, new TravelRange(4, 7));
        DAY_MAP.put(3, new TravelRange(8, 14));
        DAY_MAP.put(4, new TravelRange(15, null));

        // 平均消费
        AVG_CONSUME_MAP.put(1, new TravelRange(1, 999));
        AVG_CONSUME_MAP.put(2, new TravelRange(1000, 6000));
        AVG_CONSUME_MAP.put(3, new TravelRange(6001, 20000));
        AVG_CONSUME_MAP.put(4, new TravelRange(20001, null));
    }

    public void setConsumeType(Integer consumeType) {
        this.consumeType = AVG_CONSUME_MAP.get(consumeType);
    }
    public void setTravelTimeType(Integer travelTimeType) {
        this.travelTimeType = TRAVEL_TIME_MAP.get(travelTimeType);
    }

    public void setDayType(Integer dayType) {
        this.dayType = DAY_MAP.get(dayType);
    }

}
