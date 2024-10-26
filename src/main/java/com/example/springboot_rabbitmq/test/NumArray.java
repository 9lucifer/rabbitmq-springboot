package com.example.springboot_rabbitmq.test;

class NumArray {
    int [] nums;

    public NumArray(int[] nums) {
        this.nums = nums;
    }
    
    public int sumRange(int left, int right) {
        int [] res = new int[10010];
        res[0] = 0;
        for(int i = 1;i<nums.length;i++){
            res[i]=nums[i-1]+res[i-1];
            System.out.println(res[i]);
        }
        res[nums.length] = res[nums.length-1]+nums[nums.length-1];
        return res[right+1] - res[left];
    }

    public static void main(String[] args) {
        int[] num = {-2,0,3,-5,2,-1};
        NumArray nums = new NumArray(num);
        System.out.println(nums.sumRange(2, 5));

    }
}