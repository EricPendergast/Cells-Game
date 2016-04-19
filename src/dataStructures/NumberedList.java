package dataStructures;

public class NumberedList<T>{
	private Object[] objects;
	private int lowestLoc;
	private int highestLoc;
	private int range;
	private int length = 0;
	private int startValue;
	public NumberedList(int low, int high){
		lowestLoc = low;
		startValue = low;
		highestLoc = high;
		range = high - low;
		objects = new Object[2];
	}
	
	public void put(int loc, T obj){
//		//System.out.println("put" + this + " " + obj);
//			
//		length++;
//		Container<T> c = new Container<T>(loc,obj);
//		
//		int indexLoc = getIndexLoc(loc);
//		
//		if(objects[indexLoc] == null)//if the slot is empty
//			objects[indexLoc] = c;
//		
//		else if(objects[indexLoc] instanceof NumberedList)//if the slot contains a NumberedList
//			((NumberedList<T>)objects[indexLoc]).put(c.getLoc(),obj);
//		
//		else if (objects[indexLoc] instanceof Container){//if the slot contains another T object
//			if(((Container)objects[indexLoc]).getLoc() == c.getLoc()){		//If the the two objects are in the same location, it adds them together
//				((Container<T>)objects[indexLoc]).add(obj);
//			}
//			else{//if both values are kept, it puts a new NumberedList into the spot, then adds both objects to the NumberedList
//				int low = (int)((double)range/objects.length*indexLoc + .00001) + lowestLoc;
//				int high = (int)((double)range/objects.length*(indexLoc+1) + .00001) + lowestLoc;//+1;
//				
//				NumberedList<T> n = new NumberedList<T>(low,high);
//				//System.out.println("LH:"+low + " " + high);
//				//System.out.println("M:"+((Container)objects[indexLoc]).getLoc() + " " + c.getLoc());
//				n.put(((Container)objects[indexLoc]).getLoc(), (T)((Container)objects[indexLoc]));
//				n.put(c.getLoc(), obj);
//				
//				objects[indexLoc] = n;
//			}
//		}
//		//System.out.println(this);
		putContainer(new Container<T>(loc,obj));
	}
	private void putContainer(Container<T> c){
		//System.out.println("put" + this + " " + obj);
			
		length++;
		
		int indexLoc = getIndexLoc(c.getLoc());
		//System.out.println(c);
		if(objects[indexLoc] == null)//if the slot is empty
			objects[indexLoc] = c;
		
		else if(objects[indexLoc] instanceof NumberedList)//if the slot contains a NumberedList
			((NumberedList<T>)objects[indexLoc]).putContainer(c);
		
		else if (objects[indexLoc] instanceof Container){//if the slot contains another T object
			if(((Container)objects[indexLoc]).getLoc() == c.getLoc()){		//If the the two objects are in the same location, it adds them together
				((Container<T>)objects[indexLoc]).combine(c);
			}
			else{//if both values are kept, it puts a new NumberedList into the spot, then adds both objects to the NumberedList
				int low = (int)Math.floor((double)range/objects.length*indexLoc + .00001) + lowestLoc;
				int high = (int)Math.floor((double)range/objects.length*(indexLoc+1) + .00001) + lowestLoc;//+1;
				
				NumberedList<T> n = new NumberedList<T>(low,high);
				n.putContainer((Container)objects[indexLoc]);
				n.putContainer(c);
				
				objects[indexLoc] = n;
			}
		}
		//System.out.println(this);

	}
	//Returns an ArrayList of objects between start and end, inclusive
	public ArrayList<T> get(int start, int end){
		int startIndex = getIndexLoc(start);
		int endIndex = getIndexLoc(end);
		//System.out.println(start + " " + startIndex + " " + end + " " + endIndex);
		ArrayList<Object> list = new ArrayList<Object>();
		for(int i = startIndex; i<=endIndex; i++){
			if(objects[i] == null)
				continue;
			
			if(objects[i] instanceof Container){
				if(((Container)objects[i]).getLoc() <= end && ((Container)objects[i]).getLoc() >= start)
					((Container)objects[i]).putSelfInArrayList(list);
			}
			else
				list.add(((NumberedList)objects[i]).get(start,end));
		}
		return (ArrayList<T>)list;
	}
	//Returns a HashSet of the values between start and end, inclusive
	public HashSet<T> getSet(int start, int end){
		int startIndex = getIndexLoc(start);
		int endIndex = getIndexLoc(end);
		HashSet<T> list = new HashSet<T>();
		for(int i = startIndex; i<=endIndex; i++){
			if(objects[i] == null)
				continue;
			else if(objects[i] instanceof Container){
				if(((Container)objects[i]).getLoc() <= end && ((Container)objects[i]).getLoc() >= start)
					((Container)objects[i]).putSelfInSet(list);
			}
			else if(objects[i] instanceof NumberedList)
				list.addAll(((NumberedList<T>)objects[i]).get(start,end));
		}
		return (HashSet<T>)list;
	}
	public ArrayList<T> get(int index){
		return get(index,index);
	}
	public String toString(){
		String str = "\nOBJ{";
		for(int i = 0; i < objects.length; i++){
			if(objects[i] != null)
				str += objects[i].toString() + " " + i + " ";
		}
		str += "}\n";
		return str;
	}
	//This method translates the given location to an index in the array based on the starting an ending values
	public int getIndexLoc(int loc){
		loc -= lowestLoc*.99999;
		loc = (int)((double)loc/range * objects.length + .00001);
		if(loc < 0)
			loc = 0;
		if(loc >= objects.length)
			loc = objects.length-1;
		return loc;
	}
	//Returns an ArrayList of all the values in the list
	public ArrayList<T> getArrayList(){
		ArrayList<T> l = new ArrayList<T>();
		for(int i = 0; i < objects.length; i++){
			if(objects[i] == null)
				continue;
			if(objects[i] instanceof NumberedList)
				l.addAll(((NumberedList<T>)objects[i]).getArrayList());
			else if(objects[i] instanceof Container)
				((Container)objects[i]).putSelfInArrayList(l);
		}
		return (ArrayList<T>)l;
	}
	public int size(){
		return length;
	}
	public int max(){
		return highestLoc;
	}
	public int min(){
		return lowestLoc;
	}
	public int range(){
		return range;
	}
	public void clear(){
		for(int i = 0; i < objects.length; i++)
			objects[i] = null;
	}
	public void remove(int loc){
		int index = getIndexLoc(loc);
		
		//ArrayList<Object> list = new ArrayList<Object>();
		//for(int i = startIndex; i<=endIndex; i++){
			if(objects[index] == null)
				return;
			
			if(objects[index] instanceof Container){
				if(((Container)objects[index]).getLoc() == loc)
					//((Container)objects[index]).clear();
					objects[index] = null;
			}
			else
				((NumberedList)objects[index]).remove(loc);
		//}
		//return (ArrayList<T>)list;
	}
	public void remove(int loc, T obj){
		int index = getIndexLoc(loc);
		//System.out.println(objects[index]);
		if(objects[index] == null)
			return;
		if(objects[index] instanceof Container){
			//System.out.println(objects[index] + " " + obj);
			if(((Container)objects[index]).getLoc() == loc)
				((Container)objects[index]).remove(obj);
		}
		else
			((NumberedList)objects[index]).remove(loc,obj);
	}
}

class Container<T>{
	private int loc;
	private ArrayList<T> values;
	public Container(int loc, T obj){
		this.loc = loc;
		values = new ArrayList<T>();
		values.add(obj);
	}
	public int getLoc(){
		return loc;
	}
	public void setLoc(int loc){
		this.loc = loc;
	}
	public void putSelfInArrayList(ArrayList<T> list){
		for(int i = 0; i < values.size(); i++){
			T o = values.get(i);
			if(o != null)
				list.add(o);
		}
	}
	public void putSelfInSet(HashSet<T> set){
		for(int i = 0; i < values.size(); i++){
			T o = values.get(i);
			if(o != null)
				set.add(o);
		}
	}
	public void add(T obj){
		values.add(obj);
	}
	public void combine(Container<T> c){
		for(int i = 0; i < c.values.size(); i++)
			this.values.add(c.values.get(i));
	}
	public void remove(T obj){
		for(int i = 0; i < values.size(); i++){
			if(values.get(i) == null)
				continue;
			if(values.get(i).hashCode() == obj.hashCode())
				values.remove(i);
		}
	}
	public String toString(){
		int y = 0;
		int  j= 5/y;
		return "Container:"+values.toString();
	}
}




//public class NumberedList<T>{
//private Object[] objects;
//private int lowestLoc;
//private int highestLoc;
//private int range;
//private int length = 0;
//public NumberedList(int low, int high){
//	lowestLoc = low;
//	highestLoc = high;
//	range = high - low;
//	objects = new Object[2];
//}
//
//public void put(int loc, T obj){
//	//System.out.println("put" + this + " " + obj);
//		
//	length++;
//	Container<T> c = new Container<T>(loc,obj);
//	
//	int indexLoc = getIndexLoc(loc);
//	
//	if(objects[indexLoc] == null)//if the slot is empty
//		objects[indexLoc] = c;
//	
//	else if(objects[indexLoc] instanceof NumberedList<?>)//if the slot contains a NumberedList
//		((NumberedList<T>)objects[indexLoc]).put(c.getLoc(),(T)c.getObj());
//	
//	else if (objects[indexLoc] instanceof Container){//if the slot contains another T object
//		if(((Container)objects[indexLoc]).getLoc() == c.getLoc()){		//If the the two objects are in the same location, it adds them together
//			((Container)(objects[indexLoc])).add(c.getObj());
//		}
//		else{//if both values are kept, it puts a new NumberedList into the spot, then adds both objects to the NumberedList
//			int low = (int)((double)range/objects.length*indexLoc + .00001) + lowestLoc;
//			int high = (int)((double)range/objects.length*(indexLoc+1) + .00001) + lowestLoc;//+1;
//			
//			NumberedList<T> n = new NumberedList<T>(low,high);
//			//System.out.println("LH:"+low + " " + high);
//			//System.out.println("M:"+((Container)objects[indexLoc]).getLoc() + " " + c.getLoc());
//			n.put(((Container)objects[indexLoc]).getLoc(), (T)((Container)objects[indexLoc]).getObj());
//			n.put(c.getLoc(), (T)c.getObj());
//			
//			objects[indexLoc] = n;
//		}
//	}
//	//System.out.println(this);
//
//}
//
////Returns an ArrayList of objects between start and end, inclusive
//public ArrayList<T> get(int start, int end){
//	int startIndex = getIndexLoc(start);
//	int endIndex = getIndexLoc(end);
//	//System.out.println(start + " " + startIndex + " " + end + " " + endIndex);
//	ArrayList<Object> list = new ArrayList<Object>();
//	for(int i = startIndex; i<=endIndex; i++){
//		if(objects[i] == null)
//			continue;
//		
//		if(objects[i] instanceof Container){
//			if(((Container)objects[i]).getLoc() <= end && ((Container)objects[i]).getLoc() >= start)
//				((Container)objects[i]).putSelfInArrayList(list);
//		}
//		else
//			list.add(((NumberedList)objects[i]).get(start,end));
//	}
//	return (ArrayList<T>)list;
//}
////Returns a HashSet of the values between start and end, inclusive
//public HashSet<T> getSet(int start, int end){
//	int startIndex = getIndexLoc(start);
//	int endIndex = getIndexLoc(end);
//	HashSet<T> list = new HashSet<T>();
//	for(int i = startIndex; i<=endIndex; i++){
//		if(objects[i] == null)
//			continue;
//		else if(objects[i] instanceof Container){
//			if(((Container)objects[i]).getLoc() <= end && ((Container)objects[i]).getLoc() >= start)
//				((Container)objects[i]).putSelfInSet(list);
//		}
//		else if(objects[i] instanceof NumberedList)
//			list.addAll(((NumberedList<T>)objects[i]).get(start,end));
//	}
//	return (HashSet<T>)list;
//}
//public ArrayList<T> get(int index){
//	return get(index,index);
//}
//public String toString(){
//	String str = "\nOBJ{";
//	for(int i = 0; i < objects.length; i++){
//		if(objects[i] != null)
//			str += objects[i].toString() + " " + i + " ";
//	}
//	str += "}\n";
//	return str;
//}
////This method translates the given location to an index in the array based on the starting an ending values
//private int getIndexLoc(int loc){
//	loc -= lowestLoc;
//	loc = (int)((double)loc/range * objects.length + .00001);
//	if(loc < 0)
//		loc = 0;
//	if(loc >= objects.length)
//		loc = objects.length-1;
//	return loc;
//}
////Returns an ArrayList of all the values in the list
//public ArrayList<T> getArrayList(){
//	ArrayList<T> l = new ArrayList<T>();
//	for(int i = 0; i < objects.length; i++){
//		if(objects[i] == null)
//			continue;
//		if(objects[i] instanceof NumberedList)
//			l.addAll(((NumberedList<T>)objects[i]).getArrayList());
//		else if(objects[i] instanceof Container)
//			((Container)objects[i]).putSelfInArrayList(l);
//	}
//	return (ArrayList<T>)l;
//}
//public int size(){
//	return length;
//}
//public int max(){
//	return highestLoc;
//}
//public int min(){
//	return lowestLoc;
//}
//public int range(){
//	return range;
//}
//public void clear(){
//	for(int i = 0; i < objects.length; i++)
//		objects[i] = null;
//}
//public void remove(int loc){
//	int index = getIndexLoc(loc);
//	
//	//ArrayList<Object> list = new ArrayList<Object>();
//	//for(int i = startIndex; i<=endIndex; i++){
//		if(objects[index] == null)
//			return;
//		
//		if(objects[index] instanceof Container){
//			if(((Container)objects[index]).getLoc() == loc)
//				//((Container)objects[index]).clear();
//				objects[index] = null;
//		}
//		else
//			((NumberedList)objects[index]).remove(loc);
//	//}
//	//return (ArrayList<T>)list;
//}
//public void remove(int loc, T obj){
//	int index = getIndexLoc(loc);
//
//	if(objects[index] == null)
//		return;
//	if(objects[index] instanceof Container){
//		if(((Container)objects[index]).getLoc() == loc)
//			((Container)objects[index]).remove(obj);
//	}
//	else
//		((NumberedList)objects[index]).remove(loc);
//}
//}

//This object holds an object and its location
//class Container<T>{
//	private int loc;
//	private T obj;
//	private Container<T> container;
//	public Container(int loc, T obj){
//		this.loc = loc;
//		this.obj = obj;
//	}
//	public int getLoc(){
//		return loc;
//	}
//	public void setLoc(int loc){
//		this.loc = loc;
//	}
//	public T getObj(){
//		return obj;
//	}
//	public void setObj(T obj){
//		this.obj = obj;
//	}
//	public void add(T obj){
//		if(obj==null)
//			this.obj = obj;
//		else{
//			if(container == null)
//				container = new Container<T>(this.loc,obj);
//			else
//				container.add(obj);
//		}
//	}
//	public void putSelfInSet(HashSet<Object> set){
//		if(obj!=null)
//			set.add(obj);
//		if(container!=null)
//			container.putSelfInSet(set);
//	}
//	public void putSelfInArrayList(ArrayList<Object> arr){
//		if(obj!=null)
//			arr.add(obj);
//		if(container!=null)
//			container.putSelfInArrayList(arr);
//	}
//	public String toString(){
//		if(container == null)
//			return obj.toString();
//		else
//			return obj.toString()+"+"+container.toString();
//	}
//	public void remove(T obj){
//		
//	}
//}