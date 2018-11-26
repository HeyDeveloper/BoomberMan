package monster;

/**
 * 专门处理怪物数量的类
 * @author Guzhz
 * @date 2018年11月4日
 * @time 上午2:01:15
 */
public class MonsterCount { 
	int monster_count;
	
	public MonsterCount(int monster_count) {	
		this.monster_count = monster_count;
	}
	
	public void setReduceCount() {
		monster_count--;
		System.out.println(monster_count);
	}
	
	public boolean getState() {
		return monster_count == 0;	
	}
}
